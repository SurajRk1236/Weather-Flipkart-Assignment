package com.flikart.weather.assignment.service;

import com.flikart.weather.assignment.request.WeatherCreateRequestDTO;
import com.flikart.weather.assignment.response.WeatherForecastData;
import com.flikart.weather.assignment.response.WeatherResponseData;

import java.time.LocalDate;
import java.util.List;

public interface WeatherService {
    WeatherResponseData createWeather(WeatherCreateRequestDTO weatherCreateRequestDTO, String city);

    WeatherResponseData updateWeather(String city, WeatherCreateRequestDTO weatherUpdateRequestDTO);

    WeatherResponseData getWeatherByCity(String city);

    List<String> getAllCities();

    String deleteWeather(String city);

    List<WeatherResponseData> getHistoricalWeatherData(String city, LocalDate startDate, LocalDate endDate, Integer pageNo, Integer pageSize);

    List<WeatherResponseData> getWeatherByCityWithSorting(String city, String sortBy, String order, Integer pageNo, Integer pageSize);

    WeatherForecastData getWeatherForecast(String city);
}
