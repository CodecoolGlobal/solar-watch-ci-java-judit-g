package com.codecool.solarwatch.model.dto;

import java.time.LocalDate;

public record SunriseSunsetTimeDTO(
        int id,
        String sunrise,
        String sunset
)
{ }
