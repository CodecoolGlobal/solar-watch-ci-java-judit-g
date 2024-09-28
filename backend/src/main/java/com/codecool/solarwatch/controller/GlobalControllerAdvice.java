package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.exception.SunriseSunsetTimeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidCityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidCityExceptionHandler(InvalidCityException invalidCityException) {
        return invalidCityException.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SunriseSunsetTimeNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String sunriseSunsetTimeNotFoundExceptionHandler(SunriseSunsetTimeNotFoundException sunriseSunsetTimeNotFoundException) {
        return sunriseSunsetTimeNotFoundException.getMessage();
    }
}
