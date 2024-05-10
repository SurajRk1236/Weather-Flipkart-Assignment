package com.flikart.weather.assignment.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppConfig {
    @Value("${data.storage.db:false}")
    boolean storeToDbEnabled;

    @Value("${external.weather.api.baseUrl}")
    String externalWeatherApiBaseURL;

    @Value("${external.weather.api.apiKey}")
    String externalWeatherApiKey;
}
