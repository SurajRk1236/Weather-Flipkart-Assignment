package com.flikart.weather.assignment.service;

import com.flikart.weather.assignment.config.AppConfig;
import com.flikart.weather.assignment.entity.WeatherDataMongoHistory;
import com.flikart.weather.assignment.repository.InMemoryWeatherRepository;
import com.flikart.weather.assignment.repository.MongoWeatherRepository;
import com.flikart.weather.assignment.repository.WeatherHistoryRepositoryCustom;
import com.flikart.weather.assignment.repository.WeatherRepository;
import com.flikart.weather.assignment.request.WeatherCreateRequestDTO;
import com.flikart.weather.assignment.response.WeatherData;
import com.flikart.weather.assignment.response.WeatherForecastData;
import com.flikart.weather.assignment.response.WeatherResponseData;
import com.flikart.weather.assignment.utils.ConversionUtil;
import com.flikart.weather.assignment.utils.MapperUtils;
import com.flikart.weather.assignment.utils.RestClient;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.flikart.weather.assignment.constants.Constant.API_KEY_PARAM;
import static com.flikart.weather.assignment.constants.Constant.APPLICATION_JSON;
import static com.flikart.weather.assignment.constants.Constant.QUERY_PARAM;
import static com.flikart.weather.assignment.constants.SuccessMessageConstants.WEATHER_DELETED_SUCCESSFULLY;
import static com.flikart.weather.assignment.utils.ConversionUtil.convertToWeatherData;
import static com.flikart.weather.assignment.utils.ConversionUtil.convertToWeatherResponseData;
import static com.flikart.weather.assignment.utils.ValidationUtils.validateCity;
import static com.flikart.weather.assignment.utils.ValidationUtils.validateExistingData;
import static com.flikart.weather.assignment.utils.ValidationUtils.validatePageNoAndPageSize;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherServiceImpl implements WeatherService {

    WeatherRepository weatherRepository;

    @Autowired
    InMemoryWeatherRepository inMemoryWeatherRepository;

    @Autowired
    MongoWeatherRepository weatherDbRepository;

    @Autowired
    WeatherHistoryRepositoryCustom weatherHistoryRepositoryCustom;

    @Autowired
    AppConfig appConfig;

    @Autowired
    RestClient restClient;


    @PostConstruct
    public void init() {
        log.info("Initializing the storage based on config");
        weatherRepository = appConfig.isStoreToDbEnabled() ? weatherDbRepository : inMemoryWeatherRepository;
    }

    @Override
    public WeatherResponseData createWeather(WeatherCreateRequestDTO weatherCreateRequestDTO, String city) {
        validateCity(city);
        validateExistingData(city, weatherRepository.existsByCity(city));
        WeatherData weatherData = convertToWeatherData(weatherCreateRequestDTO);
        weatherRepository.save(weatherData, city);
        return convertToWeatherResponseData(weatherCreateRequestDTO, city);
    }

    @Override
    public WeatherResponseData updateWeather(String city, WeatherCreateRequestDTO weatherUpdateRequestDTO) {
        validateCity(city);
        validateExistingData(weatherRepository.existsByCity(city), city);
        WeatherData weatherData = convertToWeatherData(weatherUpdateRequestDTO);
        weatherRepository.save(weatherData, city);
        return convertToWeatherResponseData(weatherUpdateRequestDTO, city);
    }

    @Override
    public WeatherResponseData getWeatherByCity(String city) {
        validateCity(city);
        WeatherData weatherData = weatherRepository.findByCity(city);
        validateExistingData(weatherData, city);
        return convertToWeatherResponseData(weatherData, city);
    }

    @Override
    public List<String> getAllCities() {
        return weatherRepository.getAllCities();
    }

    @Override
    public String deleteWeather(String city) {
        weatherRepository.deleteByCity(city);
        return String.format(WEATHER_DELETED_SUCCESSFULLY, city);
    }

    @Override
    public List<WeatherResponseData> getHistoricalWeatherData(String city, LocalDate startDate, LocalDate endDate, Integer pageNo, Integer pageSize) {
        validateCity(city);
        validatePageNoAndPageSize(pageNo, pageSize);
        List<WeatherDataMongoHistory> weatherDataMongoHistoryList = weatherHistoryRepositoryCustom.getHistoricalWeatherData(city, startDate, endDate, pageNo, pageSize);
        return CollectionUtils.isEmpty(weatherDataMongoHistoryList) ?
                Collections.emptyList() :
                weatherDataMongoHistoryList.stream()
                        .map(ConversionUtil::convertToWeatherResponseData)
                        .collect(Collectors.toList());
    }

    @Override
    public List<WeatherResponseData> getWeatherByCityWithSorting(String city, String sortBy, String order, Integer pageNo, Integer pageSize) {
        validateCity(city);
        validatePageNoAndPageSize(pageNo, pageSize);
        List<WeatherDataMongoHistory> weatherDataMongoHistoryList = weatherHistoryRepositoryCustom.getWeatherByCityWithSorting(city, sortBy, order, pageNo, pageSize);
        return CollectionUtils.isEmpty(weatherDataMongoHistoryList) ?
                Collections.emptyList() :
                weatherDataMongoHistoryList.stream()
                        .map(ConversionUtil::convertToWeatherResponseData)
                        .collect(Collectors.toList());
    }


    @Override
    public WeatherForecastData getWeatherForecast(String city) {
        validateCity(city);
        String response = restClient.doGetRequest(appConfig.getExternalWeatherApiBaseURL(), getParamsForWeather(city), getHeaders());
        return MapperUtils.readValue(response, WeatherForecastData.class);
    }

    private Map<String, String> getParamsForWeather(String city) {
        Map<String, String> params = new HashMap<>();
        params.put(API_KEY_PARAM, appConfig.getExternalWeatherApiKey());
        params.put(QUERY_PARAM, city);
        return params;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, APPLICATION_JSON);
        return headers;
    }
}
