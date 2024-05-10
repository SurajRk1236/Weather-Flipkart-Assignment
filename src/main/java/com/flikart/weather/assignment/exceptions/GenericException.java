package com.flikart.weather.assignment.exceptions;

import com.flikart.weather.assignment.enums.FlipkartErrorResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class GenericException extends RuntimeException {
    FlipkartErrorResponse errorResponse;
    List<String> args;

    public GenericException(FlipkartErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
