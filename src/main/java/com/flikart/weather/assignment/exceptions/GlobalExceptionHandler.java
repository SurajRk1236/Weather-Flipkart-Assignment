package com.flikart.weather.assignment.exceptions;

import com.flikart.weather.assignment.response.FlipkartCommonResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public FlipkartCommonResponse<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return getFlipkartErrorResponse(e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", ")));
    }

    @ExceptionHandler(GenericException.class)
    @ResponseBody
    public FlipkartCommonResponse<?> handleException(GenericException exception) {
        return getFlipkartErrorResponse(exception);
    }

    private FlipkartCommonResponse<?> getFlipkartErrorResponse(GenericException exception) {
        return FlipkartCommonResponse.builder()
                .errorMessage(exception.getErrorResponse().isLocalized() ?
                        MessageFormat.format(exception.getErrorResponse().getMessage(), exception.getArgs())
                        : exception.getErrorResponse().getMessage())
                .code(exception.getErrorResponse().getCode()).build();
    }

    private FlipkartCommonResponse<?> getFlipkartErrorResponse(String message) {
        return FlipkartCommonResponse.builder()
                .errorMessage(message).build();
    }
}
