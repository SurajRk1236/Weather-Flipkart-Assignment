package com.flikart.weather.assignment.repository;

import com.flikart.weather.assignment.response.WeatherData;

import java.util.List;

public interface WeatherRepository {

    void save(WeatherData weatherData, String city);

    WeatherData findByCity(String city);

    List<String> getAllCities();

    void deleteByCity(String city);

    boolean existsByCity(String city);
}
