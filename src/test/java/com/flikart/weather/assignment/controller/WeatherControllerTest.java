package com.flikart.weather.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flikart.weather.assignment.request.WeatherCreateRequestDTO;
import com.flikart.weather.assignment.response.WeatherForecastData;
import com.flikart.weather.assignment.response.WeatherResponseData;
import com.flikart.weather.assignment.service.WeatherService;

import java.time.LocalDate;
import java.util.Collections;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.flikart.weather.assignment.constants.Constant.PAGE_NO;
import static com.flikart.weather.assignment.constants.Constant.PAGE_SIZE;
import static com.flikart.weather.assignment.constants.SuccessMessageConstants.WEATHER_DELETED_SUCCESSFULLY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@FieldDefaults(level = AccessLevel.PRIVATE)
class WeatherControllerTest {
    @InjectMocks
    WeatherController weatherController = new WeatherController();

    @Mock
    WeatherService weatherService;


    private static final EasyRandom generator = new EasyRandom();
    private static final String CITY = "BANGALORE";
    private static final String SORT = "temperature";
    private static final String DESC = "DESC";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String SORT_BY = "sortBy";
    private static final String ORDER = "order";

    WeatherCreateRequestDTO weatherCreateRequestDTO;
    WeatherForecastData weatherForecastData;
    WeatherResponseData weatherResponseData;
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        weatherCreateRequestDTO = generator.nextObject(WeatherCreateRequestDTO.class);
        weatherForecastData = generator.nextObject(WeatherForecastData.class);
        weatherResponseData = generator.nextObject(WeatherResponseData.class);
        mockMvc =
                MockMvcBuilders.standaloneSetup(weatherController)
                        .build();
    }

    @Test
    void testCreateWeather() throws Exception {
        when(weatherService.createWeather(any(), any())).thenReturn(weatherResponseData);
        MockHttpServletResponse response = mockMvc.perform(post("/weather/{city}", CITY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(weatherCreateRequestDTO)))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testDeleteWeather() throws Exception {
        when(weatherService.deleteWeather(any())).thenReturn(String.format(WEATHER_DELETED_SUCCESSFULLY, CITY));
        MockHttpServletResponse response = mockMvc.perform
                        (delete("/weather/{city}", CITY))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testGetAllCities() throws Exception {
        when(weatherService.getAllCities()).thenReturn(Collections.singletonList(CITY));
        MockHttpServletResponse response = mockMvc.perform
                        (get("/weather/all"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testGetHistoricalWeatherData() throws Exception {
        when(weatherService.getHistoricalWeatherData(any(), any(), any(), any(), any())).thenReturn(Collections.singletonList(weatherResponseData));
        MockHttpServletResponse response = mockMvc.perform
                        (get("/weather/{city}/history", CITY)
                                .param(START_DATE, String.valueOf(LocalDate.of(2024, 1, 1)))
                                .param(END_DATE, String.valueOf(LocalDate.of(2024, 1, 10)))
                                .param(PAGE_NO, String.valueOf(1))
                                .param(PAGE_SIZE, String.valueOf(10))
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testGetWeatherByCity() throws Exception {
        when(weatherService.getWeatherByCity(any())).thenReturn(weatherResponseData);
        MockHttpServletResponse response = mockMvc.perform
                (get("/weather/{city}", CITY)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testGetWeatherByCityWithSorting() throws Exception {
        when(weatherService.getWeatherByCityWithSorting(any(), any(), any(), any(), any())).thenReturn(Collections.singletonList(weatherResponseData));
        MockHttpServletResponse response = mockMvc.perform
                        (get("/weather/{city}/sort", CITY)
                                .param(SORT_BY, SORT)
                                .param(ORDER, DESC)
                                .param(PAGE_NO, String.valueOf(1))
                                .param(PAGE_SIZE, String.valueOf(10))
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testGetWeatherForecast() throws Exception {
        when(weatherService.getWeatherForecast(any())).thenReturn(weatherForecastData);
        MockHttpServletResponse response = mockMvc.perform
                (get("/weather/{city}/forecast", CITY)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testUpdateWeather() throws Exception {
        when(weatherService.updateWeather(any(), any())).thenReturn(weatherResponseData);
        MockHttpServletResponse response = mockMvc.perform
                        (put("/weather/{city}", CITY)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(weatherCreateRequestDTO)))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
