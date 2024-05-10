package com.flikart.weather.assignment.utils;

import com.flikart.weather.assignment.exceptions.GenericException;
import com.flikart.weather.assignment.response.WeatherData;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;

import static com.flikart.weather.assignment.enums.FlipkartErrorResponse.*;

public class ValidationUtils {
    public static void validateCity(String city) {
        if (StringUtils.isBlank(city))
            throw new GenericException(FKE001);
    }

    public static void validateExistingData(boolean existingDataPresent, String city) {
        if (!existingDataPresent)
            throw new GenericException(FKE002, Collections.singletonList(city));
    }

    public static void validateExistingData(String city, boolean existingDataPresent) {
        if (existingDataPresent)
            throw new GenericException(FKE003, Collections.singletonList(city));
    }

    public static void validateExistingData(WeatherData weatherData, String city) {
        if (weatherData == null)
            throw new GenericException(FKE002, Collections.singletonList(city));
    }

    public static void validatePageNoAndPageSize(Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo < 0)
            throw new GenericException(FKE004);
        if (pageSize == null || pageSize <= 0 || pageSize > 100)
            throw new GenericException(FKE005);
    }
}
