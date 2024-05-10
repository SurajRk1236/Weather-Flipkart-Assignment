package com.flikart.weather.assignment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flikart.weather.assignment.config.AppConfig;
import com.flikart.weather.assignment.entity.WeatherDataMongoHistory;
import com.flikart.weather.assignment.exceptions.GenericException;
import com.flikart.weather.assignment.repository.InMemoryWeatherRepository;
import com.flikart.weather.assignment.repository.MongoWeatherRepository;
import com.flikart.weather.assignment.repository.WeatherHistoryRepositoryCustom;
import com.flikart.weather.assignment.repository.WeatherRepository;
import com.flikart.weather.assignment.request.WeatherCreateRequestDTO;
import com.flikart.weather.assignment.response.WeatherData;
import com.flikart.weather.assignment.response.WeatherForecastData;
import com.flikart.weather.assignment.utils.RestClient;

import java.time.LocalDate;
import java.util.Collections;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.flikart.weather.assignment.constants.SuccessMessageConstants.WEATHER_DELETED_SUCCESSFULLY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
class WeatherServiceImplTest {

    @InjectMocks
    WeatherServiceImpl weatherServiceImpl = new WeatherServiceImpl();

    @Mock
    InMemoryWeatherRepository inMemoryWeatherRepository;

    @Mock
    MongoWeatherRepository mongoWeatherRepository;

    @Mock
    RestClient restClient;

    @Mock
    WeatherHistoryRepositoryCustom weatherHistoryRepositoryCustom;

    @Mock
    AppConfig appConfig;

    private static final EasyRandom generator = new EasyRandom();
    private static final String CITY = "BANGALORE";
    private static final String SORT = "temperature";
    private static final String DESC = "DESC";

    WeatherRepository weatherRepository;
    WeatherCreateRequestDTO weatherCreateRequestDTO;
    WeatherData weatherData;
    WeatherDataMongoHistory weatherDataMongoHistory;
    WeatherForecastData weatherForecastData;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        weatherRepository = inMemoryWeatherRepository;
        weatherCreateRequestDTO = generator.nextObject(WeatherCreateRequestDTO.class);
        weatherData = generator.nextObject(WeatherData.class);
        weatherDataMongoHistory = generator.nextObject(WeatherDataMongoHistory.class);
        weatherForecastData = generator.nextObject(WeatherForecastData.class);
    }

    @Test
    void testInitForStoreToDbEnabledTrue() {
        when(appConfig.isStoreToDbEnabled()).thenReturn(true);
        assertDoesNotThrow(() -> weatherServiceImpl.init());
    }

    @Test
    void testInitForStoreToDbEnabledFalse() {
        when(appConfig.isStoreToDbEnabled()).thenReturn(true);
        weatherServiceImpl.init();
        assertDoesNotThrow(() -> weatherServiceImpl.init());
    }

    @Test
    void testCreateWeatherForCityNULL() {
        assertThrows(GenericException.class, () -> weatherServiceImpl.createWeather(weatherCreateRequestDTO, null));
    }

    @Test
    void testCreateWeatherForExistingDataTRUE() {
        when(appConfig.isStoreToDbEnabled()).thenReturn(true);
        weatherServiceImpl.init();
        when(weatherRepository.existsByCity(anyString())).thenReturn(true);
        when(mongoWeatherRepository.existsByCity(anyString())).thenReturn(true);
        assertThrows(GenericException.class, () -> weatherServiceImpl.createWeather(weatherCreateRequestDTO, CITY));
    }

    @Test
    void testCreateWeather() {
        when(appConfig.isStoreToDbEnabled()).thenReturn(true);
        weatherServiceImpl.init();
        when(weatherRepository.existsByCity(anyString())).thenReturn(false);
        assertDoesNotThrow(() -> weatherServiceImpl.createWeather(weatherCreateRequestDTO, CITY));
    }

    @Test
    void testUpdateWeatherForCityNULL() {
        assertThrows(GenericException.class, () -> weatherServiceImpl.updateWeather(null, weatherCreateRequestDTO));
    }

    @Test
    void testUpdateWeatherForExistingDataFALSE() {
        when(appConfig.isStoreToDbEnabled()).thenReturn(true);
        weatherServiceImpl.init();
        when(weatherRepository.existsByCity(anyString())).thenReturn(false);
        assertThrows(GenericException.class, () -> weatherServiceImpl.updateWeather(CITY, weatherCreateRequestDTO));
    }

    @Test
    void testUpdateWeather() {
        when(appConfig.isStoreToDbEnabled()).thenReturn(true);
        weatherServiceImpl.init();
        when(weatherRepository.existsByCity(anyString())).thenReturn(true);
        when(mongoWeatherRepository.existsByCity(anyString())).thenReturn(true);
        assertDoesNotThrow(() -> weatherServiceImpl.updateWeather(CITY, weatherCreateRequestDTO));
    }

    @Test
    void testGetWeatherByCityNoExistingDataPresentWithNULL() {
        when(appConfig.isStoreToDbEnabled()).thenReturn(true);
        weatherServiceImpl.init();
        when(weatherRepository.findByCity(anyString())).thenReturn(null);
        assertThrows(GenericException.class, () -> weatherServiceImpl.getWeatherByCity(CITY));
    }

    @Test
    void testGetWeatherByCity() {
        when(appConfig.isStoreToDbEnabled()).thenReturn(true);
        weatherServiceImpl.init();
        when(weatherRepository.findByCity(anyString())).thenReturn(weatherData);
        when(mongoWeatherRepository.findByCity(anyString())).thenReturn(weatherData);
        assertDoesNotThrow(() -> weatherServiceImpl.getWeatherByCity(CITY));
    }

    @Test
    void testGetAllCities() {
        when(appConfig.isStoreToDbEnabled()).thenReturn(true);
        weatherServiceImpl.init();
        when(weatherRepository.getAllCities()).thenReturn(Collections.singletonList(CITY));
        when(mongoWeatherRepository.getAllCities()).thenReturn(Collections.singletonList(CITY));
        assertThat(weatherServiceImpl.getAllCities()).isEqualTo(Collections.singletonList(CITY));
    }

    @Test
    void testDeleteWeather() {
        when(appConfig.isStoreToDbEnabled()).thenReturn(true);
        weatherServiceImpl.init();
        assertThat(weatherServiceImpl.deleteWeather(CITY)).isEqualTo(String.format(WEATHER_DELETED_SUCCESSFULLY, CITY));
    }

    @Test
    void testGetHistoricalWeatherDataWithPageNoNULL() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 10);
        assertThrows(GenericException.class, () -> weatherServiceImpl.getHistoricalWeatherData(CITY, startDate, endDate, null, 3));
    }

    @Test
    void testGetHistoricalWeatherDataWithPageNoNegative() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 10);
        assertThrows(GenericException.class, () -> weatherServiceImpl.getHistoricalWeatherData(CITY, startDate, endDate, -1, 3));
    }

    @Test
    void testGetHistoricalWeatherDataWithPageSizeNULL() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 10);
        assertThrows(GenericException.class, () -> weatherServiceImpl.getHistoricalWeatherData(CITY, startDate, endDate, 1, null));
    }

    @Test
    void testGetHistoricalWeatherDataWithPageSizeNegative() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 10);
        assertThrows(GenericException.class, () -> weatherServiceImpl.getHistoricalWeatherData(CITY, startDate, endDate, 1, -3));
    }

    @Test
    void testGetHistoricalWeatherDataWithPageSizeMoreThan100() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 10);
        assertThrows(GenericException.class, () -> weatherServiceImpl.getHistoricalWeatherData(CITY, startDate, endDate, 1, 125));
    }

    @Test
    void testGetHistoricalWeatherDataWithNoDataInDb() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 10);
        when(weatherHistoryRepositoryCustom.getHistoricalWeatherData(any(), any(), any(), any(), any())).thenReturn(null);
        assertThat(weatherServiceImpl.getHistoricalWeatherData(CITY, startDate, endDate, 0, 10)).isEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    void testGetHistoricalWeatherData() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 10);
        when(weatherHistoryRepositoryCustom.getHistoricalWeatherData(any(), any(), any(), any(), any())).thenReturn(Collections.singletonList(weatherDataMongoHistory));
        assertDoesNotThrow(() -> weatherServiceImpl.getHistoricalWeatherData(CITY, startDate, endDate, 0, 10));
    }


    @Test
    void testGetWeatherByCityWithSortingWithNoDataInDb() {
        when(weatherHistoryRepositoryCustom.getWeatherByCityWithSorting(any(), any(), any(), any(), any())).thenReturn(null);
        assertThat(weatherServiceImpl.getWeatherByCityWithSorting(CITY, SORT, DESC, 1, 3)).isEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    void testGetWeatherByCityWithSorting() {
        when(weatherHistoryRepositoryCustom.getWeatherByCityWithSorting(any(), any(), any(), any(), any())).thenReturn(Collections.singletonList(weatherDataMongoHistory));
        assertDoesNotThrow(() -> weatherServiceImpl.getWeatherByCityWithSorting(CITY, SORT, DESC, 1, 3));
    }

    @SneakyThrows
    @Test
    void testGetWeatherForecast() {
        when(restClient.doGetRequest(any(), any(), any())).thenReturn(new ObjectMapper().writeValueAsString(weatherForecastData));
        assertDoesNotThrow(() -> weatherServiceImpl.getWeatherForecast(CITY));
    }
}
