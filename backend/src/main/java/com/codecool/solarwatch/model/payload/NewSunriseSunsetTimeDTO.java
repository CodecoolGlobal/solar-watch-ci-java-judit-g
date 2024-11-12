package com.codecool.solarwatch.model.payload;

import java.time.LocalDate;

public record NewSunriseSunsetTimeDTO(
        String sunrise,
        String sunset,
        LocalDate date,
        SunriseSunsetTimeCityNameDTO sunriseSunsetTimeCityNameDTO) { }
