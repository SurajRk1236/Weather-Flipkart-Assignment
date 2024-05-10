package com.flikart.weather.assignment.repository;

import com.flikart.weather.assignment.entity.WeatherDataMongo;
import com.flikart.weather.assignment.enums.FlipkartErrorResponse;
import com.flikart.weather.assignment.exceptions.GenericException;
import com.flikart.weather.assignment.response.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.flikart.weather.assignment.utils.ConversionUtil.*;

@Service
public class MongoWeatherRepository implements WeatherRepository {
    @Autowired
    WeatherDbRepository weatherDbRepository;

    @Autowired
    WeatherHistoryRepository weatherHistoryRepository;

    @Override
    public void save(WeatherData weatherData, String city) {
        weatherDbRepository.save(convertToWeatherDataMongoEntity(weatherData, city));
        weatherHistoryRepository.save(convertToWeatherDataMongoHistoryEntity(weatherData, city));
    }

    @Override
    public WeatherData findByCity(String city) {
        WeatherDataMongo weatherDataMongoEntity = weatherDbRepository.findById(city)
                .orElseThrow(() -> new GenericException(FlipkartErrorResponse.FKE002, Collections.singletonList(city)));
        return convertToWeatherData(weatherDataMongoEntity);
    }

    @Override
    public List<String> getAllCities() {
        return weatherDbRepository.findAllIds();
    }

    @Override
    public void deleteByCity(String city) {
        weatherDbRepository.deleteById(city);
    }

    @Override
    public boolean existsByCity(String city) {
        return weatherDbRepository.existsById(city);
    }
}
