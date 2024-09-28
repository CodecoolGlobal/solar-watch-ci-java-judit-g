package com.codecool.solarwatch.model.dto;

public record NewCityDTO(
        String cityName,
        double longitude,
        double latitude,
        String state,
        String country
)
{ }
