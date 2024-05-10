package com.flikart.weather.assignment.enums;

import lombok.Getter;

@Getter
public enum FlipkartErrorResponse {

    FKE001("FKE001", "City cannot be empty.", false),
    FKE002("FKE002", "Weather data does not exist for {0} in the system.", true),
    FKE003("FKE003", "Weather data already exist for {0} in the system.", true),
    FKE004("FKE004", "Page number must be a positive integer greater than 0.", false),
    FKE005("FKE005", "Page size must be a positive integer greater than 0 and less than or equal to 100.", false),
    FKE006("FKE006", "Exception occurred while calling third party api with message {0} .", true);

    final String code;
    final String message;
    final boolean isLocalized;

    FlipkartErrorResponse(String code, String message, boolean isLocalized) {
        this.code = code;
        this.message = message;
        this.isLocalized = isLocalized;
    }

}
