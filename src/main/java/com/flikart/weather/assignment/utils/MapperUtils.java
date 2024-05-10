package com.flikart.weather.assignment.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class MapperUtils {

    @SneakyThrows
    public static <T> T readValue(String orderDetails, Class<T> objectClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(orderDetails, objectClass);
    }
}
