package com.flikart.weather.assignment.repository;

import com.flikart.weather.assignment.response.WeatherData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryWeatherRepository implements WeatherRepository {

    private final Map<String, WeatherData> weatherDataMap = new HashMap<>();

    @Override
    public void save(WeatherData weatherData, String city) {
        weatherDataMap.put(city, weatherData);
    }

    @Override
    public WeatherData findByCity(String city) {
        return weatherDataMap.get(city);
    }

    @Override
    public List<String> getAllCities() {
        return new ArrayList<>(weatherDataMap.keySet());
    }

    @Override
    public void deleteByCity(String city) {
        weatherDataMap.remove(city);
    }

    @Override
    public boolean existsByCity(String city) {
        return weatherDataMap.containsKey(city);
    }
}
