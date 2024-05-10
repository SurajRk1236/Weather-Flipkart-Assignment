package com.flikart.weather.assignment.repository;

import com.flikart.weather.assignment.response.WeatherData;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class InMemoryWeatherRepositoryTest {
    @InjectMocks
    InMemoryWeatherRepository inMemoryWeatherRepository = new InMemoryWeatherRepository();

    private static final EasyRandom generator = new EasyRandom();
    private static final String CITY = "BANGALORE";

    WeatherData weatherData;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        inMemoryWeatherRepository.save(weatherData, CITY);
        weatherData = generator.nextObject(WeatherData.class);
    }

    @Test
    void testSave() {
        assertDoesNotThrow(() -> inMemoryWeatherRepository.save(weatherData, CITY));
    }

    @Test
    void testFindByCity() {
        assertDoesNotThrow(() -> inMemoryWeatherRepository.findByCity(CITY));
    }

    @Test
    void testGetAllCities() {
        assertThat(inMemoryWeatherRepository.getAllCities()).isEqualTo(Collections.singletonList(CITY));
    }

    @Test
    void testDeleteByCity() {
        inMemoryWeatherRepository.deleteByCity(CITY);
        assertTrue(inMemoryWeatherRepository.getAllCities().isEmpty());
    }

    @Test
    void testExistsByCity() {
        assertTrue(inMemoryWeatherRepository.existsByCity(CITY));
    }
}
