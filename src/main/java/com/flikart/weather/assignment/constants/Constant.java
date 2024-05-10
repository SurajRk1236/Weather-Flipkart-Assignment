package com.flikart.weather.assignment.constants;

import com.flikart.weather.assignment.entity.WeatherDataMongoHistory.Fields;

import java.util.Arrays;
import java.util.List;

public class Constant {

    public static final List<String> VALID_SORT_FIELDS = Arrays.asList(
            Fields.city,
            Fields.temperature,
            Fields.description,
            Fields.createdAt
    );

    public static final String PAGE_NO = "pageNo";
    public static final String PAGE_SIZE = "pageSize";
    public static final String API_KEY_PARAM = "key";
    public static final String QUERY_PARAM = "q";
    public static final String APPLICATION_JSON = "application/json";

}
