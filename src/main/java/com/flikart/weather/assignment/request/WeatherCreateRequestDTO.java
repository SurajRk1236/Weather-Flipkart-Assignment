package com.flikart.weather.assignment.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;


import static com.flikart.weather.assignment.constants.ErrorMessageConstants.DESCRIPTION_NOT_NULL;
import static com.flikart.weather.assignment.constants.ErrorMessageConstants.TEMPERATURE_NOT_NULL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherCreateRequestDTO {
    @NotNull(message = TEMPERATURE_NOT_NULL)
    Double temperature;
    @NotNull(message = DESCRIPTION_NOT_NULL)
    String description;
}
