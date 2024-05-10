package com.flikart.weather.assignment.controller;

import com.flikart.weather.assignment.request.WeatherCreateRequestDTO;
import com.flikart.weather.assignment.response.FlipkartCommonResponse;
import com.flikart.weather.assignment.response.WeatherForecastData;
import com.flikart.weather.assignment.response.WeatherResponseData;
import com.flikart.weather.assignment.service.WeatherService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static com.flikart.weather.assignment.constants.Constant.PAGE_NO;
import static com.flikart.weather.assignment.constants.Constant.PAGE_SIZE;

@RestController
@RequestMapping("/weather")
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @PostMapping(value = "/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FlipkartCommonResponse<WeatherResponseData> createWeather(@PathVariable String city, @RequestBody @Valid WeatherCreateRequestDTO weatherCreateRequestDTO) {
        return FlipkartCommonResponse.<WeatherResponseData>builder().data(weatherService.createWeather(weatherCreateRequestDTO, city)).build();
    }

    @PutMapping(value = "/{city}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FlipkartCommonResponse<WeatherResponseData> updateWeather(@PathVariable String city, @RequestBody @Valid WeatherCreateRequestDTO weatherUpdateRequestDTO) {
        return FlipkartCommonResponse.<WeatherResponseData>builder().data(weatherService.updateWeather(city, weatherUpdateRequestDTO)).build();
    }

    @GetMapping(value = "/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FlipkartCommonResponse<WeatherResponseData> getWeatherByCity(@PathVariable String city) {
        return FlipkartCommonResponse.<WeatherResponseData>builder().data(weatherService.getWeatherByCity(city)).build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public FlipkartCommonResponse<List<String>> getAllCities() {
        return FlipkartCommonResponse.<List<String>>builder().data(weatherService.getAllCities()).build();
    }

    @DeleteMapping(value = "/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FlipkartCommonResponse<String> deleteWeather(@PathVariable String city) {
        return FlipkartCommonResponse.<String>builder().data(weatherService.deleteWeather(city)).build();
    }

    @GetMapping(value = "/{city}/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public FlipkartCommonResponse<List<WeatherResponseData>> getHistoricalWeatherData(
            @PathVariable String city,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = PAGE_NO) Integer pageNo,
            @RequestParam(value = PAGE_SIZE) Integer pageSize) {
        return FlipkartCommonResponse.<List<WeatherResponseData>>builder().data(weatherService.getHistoricalWeatherData(city, startDate, endDate, pageNo, pageSize)).build();
    }

    @GetMapping(value = "/{city}/sort", produces = MediaType.APPLICATION_JSON_VALUE)
    public FlipkartCommonResponse<List<WeatherResponseData>> getWeatherByCityWithSorting(
            @PathVariable String city,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order,
            @RequestParam(value = PAGE_NO) Integer pageNo,
            @RequestParam(value = PAGE_SIZE) Integer pageSize) {
        return FlipkartCommonResponse.<List<WeatherResponseData>>builder().data(weatherService.getWeatherByCityWithSorting(city, sortBy, order, pageNo, pageSize)).build();
    }

    @GetMapping(value = "/{city}/forecast", produces = MediaType.APPLICATION_JSON_VALUE)
    public FlipkartCommonResponse<WeatherForecastData> getWeatherForecast(@PathVariable String city) {
        return FlipkartCommonResponse.<WeatherForecastData>builder().data(weatherService.getWeatherForecast(city)).build();
    }

}
