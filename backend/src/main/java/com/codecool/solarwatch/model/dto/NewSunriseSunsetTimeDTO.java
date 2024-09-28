package com.codecool.solarwatch.model.dto;

import java.time.LocalDate;

public record NewSunriseSunsetTimeDTO(
        String sunrise,
        String sunset,
        LocalDate date,
        SunriseSunsetTimeCityNameDTO sunriseSunsetTimeCityNameDTO) { }
