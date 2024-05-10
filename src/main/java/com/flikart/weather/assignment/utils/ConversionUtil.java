package com.flikart.weather.assignment.utils;

import com.flikart.weather.assignment.entity.WeatherDataMongo;
import com.flikart.weather.assignment.entity.WeatherDataMongoHistory;
import com.flikart.weather.assignment.request.WeatherCreateRequestDTO;
import com.flikart.weather.assignment.response.WeatherData;
import com.flikart.weather.assignment.response.WeatherResponseData;

public class ConversionUtil {

    public static WeatherData convertToWeatherData(WeatherCreateRequestDTO requestDTO) {
        return WeatherData.builder()
                .temperature(requestDTO.getTemperature())
                .description(requestDTO.getDescription())
                .build();
    }

    public static WeatherResponseData convertToWeatherResponseData(WeatherCreateRequestDTO requestDTO, String city) {
        return WeatherResponseData.builder()
                .city(city)
                .temperature(requestDTO.getTemperature())
                .description(requestDTO.getDescription())
                .build();
    }

    public static WeatherResponseData convertToWeatherResponseData(WeatherData weatherData, String city) {
        return WeatherResponseData.builder()
                .city(city)
                .temperature(weatherData.getTemperature())
                .description(weatherData.getDescription())
                .build();
    }

    public static WeatherDataMongo convertToWeatherDataMongoEntity(WeatherData weatherData, String city) {
        return WeatherDataMongo.builder()
                .id(city)
                .temperature(weatherData.getTemperature())
                .description(weatherData.getDescription())
                .build();
    }

    public static WeatherData convertToWeatherData(WeatherDataMongo weatherDataMongo) {
        return WeatherData.builder()
                .temperature(weatherDataMongo.getTemperature())
                .description(weatherDataMongo.getDescription())
                .build();
    }

    public static WeatherDataMongoHistory convertToWeatherDataMongoHistoryEntity(WeatherData weatherData, String city) {
        return WeatherDataMongoHistory.builder()
                .city(city)
                .temperature(weatherData.getTemperature())
                .description(weatherData.getDescription())
                .build();
    }

    public static WeatherResponseData convertToWeatherResponseData(WeatherDataMongoHistory weatherDataMongoHistory) {
        return WeatherResponseData.builder()
                .city(weatherDataMongoHistory.getCity())
                .temperature(weatherDataMongoHistory.getTemperature())
                .description(weatherDataMongoHistory.getDescription())
                .createdAt(weatherDataMongoHistory.getCreatedAt())
                .build();
    }
}
