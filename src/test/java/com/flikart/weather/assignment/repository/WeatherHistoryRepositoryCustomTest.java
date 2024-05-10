package com.flikart.weather.assignment.repository;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@FieldDefaults(level = AccessLevel.PRIVATE)
class WeatherHistoryRepositoryCustomTest {

    @Mock
    MongoTemplate mongoTemplate;

    @InjectMocks
    WeatherHistoryRepositoryCustom weatherHistoryRepositoryCustom = new WeatherHistoryRepositoryCustom(mongoTemplate);

    private static final String CITY = "BANGALORE";
    private static final String SORT = "temperature";
    private static final String DESC = "DESC";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetWeatherByCityWithSorting() {
        assertDoesNotThrow(() -> weatherHistoryRepositoryCustom.getWeatherByCityWithSorting(CITY, SORT, DESC, 1, 3));
    }

    @Test
    void testGetHistoricalWeatherData() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 10);
        assertDoesNotThrow(() -> weatherHistoryRepositoryCustom.getHistoricalWeatherData(CITY, startDate, endDate, 1, 3));
    }
}
