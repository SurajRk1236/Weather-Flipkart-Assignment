package com.flikart.weather.assignment.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.flikart.weather.assignment.entity.WeatherDataMongo;
import com.flikart.weather.assignment.exceptions.GenericException;
import com.flikart.weather.assignment.response.WeatherData;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
class MongoWeatherRepositoryTest {
    @InjectMocks
    MongoWeatherRepository mongoWeatherRepository = new MongoWeatherRepository();

    @Mock
    WeatherDbRepository weatherDbRepository;

    @Mock
    WeatherHistoryRepository weatherHistoryRepository;

    private static final EasyRandom generator = new EasyRandom();
    private static final String CITY = "BANGALORE";

    WeatherData weatherData;
    WeatherDataMongo weatherDataMongo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        weatherData = generator.nextObject(WeatherData.class);
        weatherDataMongo = generator.nextObject(WeatherDataMongo.class);
    }

    @Test
    void testSave() {
        assertDoesNotThrow(() -> mongoWeatherRepository.save(weatherData, CITY));
    }

    @Test
    void testFindByCityWithDbDataAsNULL() {
        when(weatherDbRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(GenericException.class, () -> mongoWeatherRepository.findByCity(CITY));
    }

    @Test
    void testFindByCity() {
        when(weatherDbRepository.findById(any())).thenReturn(Optional.ofNullable(weatherDataMongo));
        assertDoesNotThrow(() -> mongoWeatherRepository.findByCity(CITY));
    }

    @Test
    void testGetAllCities() {
        when(weatherDbRepository.findAllIds()).thenReturn(Collections.singletonList(CITY));
        assertThat(mongoWeatherRepository.getAllCities()).isEqualTo(Collections.singletonList(CITY));
    }

    @Test
    void testDeleteByCity() {
        assertDoesNotThrow(() -> mongoWeatherRepository.deleteByCity(CITY));
    }

    @Test
    void testExistsByCity() {
        when(weatherDbRepository.existsById(any())).thenReturn(true);
        assertTrue(mongoWeatherRepository.existsByCity(CITY));
    }
}
