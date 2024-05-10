package com.flikart.weather.assignment.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherForecastData {

    @JsonProperty("location")
    LocationDTO location;
    @JsonProperty("current")
    CurrentDTO current;

    @NoArgsConstructor
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class LocationDTO {
        @JsonProperty("name")
        String name;
        @JsonProperty("region")
        String region;
        @JsonProperty("country")
        String country;
        @JsonProperty("lat")
        Double lat;
        @JsonProperty("lon")
        Double lon;
        @JsonProperty("tz_id")
        String tzId;
        @JsonProperty("localtime_epoch")
        Integer localtimeEpoch;
        @JsonProperty("localtime")
        String localtime;
    }

    @NoArgsConstructor
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CurrentDTO {
        @JsonProperty("last_updated_epoch")
        Integer lastUpdatedEpoch;
        @JsonProperty("last_updated")
        String lastUpdated;
        @JsonProperty("temp_c")
        Double tempC;
        @JsonProperty("temp_f")
        Double tempF;
        @JsonProperty("is_day")
        Integer isDay;
        @JsonProperty("condition")
        ConditionDTO condition;
        @JsonProperty("wind_mph")
        Double windMph;
        @JsonProperty("wind_kph")
        Double windKph;
        @JsonProperty("wind_degree")
        Integer windDegree;
        @JsonProperty("wind_dir")
        String windDir;
        @JsonProperty("pressure_mb")
        Double pressureMb;
        @JsonProperty("pressure_in")
        Double pressureIn;
        @JsonProperty("precip_mm")
        Double precipMm;
        @JsonProperty("precip_in")
        Double precipIn;
        @JsonProperty("humidity")
        Integer humidity;
        @JsonProperty("cloud")
        Integer cloud;
        @JsonProperty("feelslike_c")
        Double feelslikeC;
        @JsonProperty("feelslike_f")
        Double feelslikeF;
        @JsonProperty("vis_km")
        Double visKm;
        @JsonProperty("vis_miles")
        Double visMiles;
        @JsonProperty("uv")
        Double uv;
        @JsonProperty("gust_mph")
        Double gustMph;
        @JsonProperty("gust_kph")
        Double gustKph;

        @NoArgsConstructor
        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        @FieldDefaults(level = AccessLevel.PRIVATE)
        public static class ConditionDTO {
            @JsonProperty("text")
            String text;
            @JsonProperty("icon")
            String icon;
            @JsonProperty("code")
            Integer code;
        }
    }
}
