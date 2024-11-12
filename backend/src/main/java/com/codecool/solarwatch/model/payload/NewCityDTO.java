package com.codecool.solarwatch.model.payload;

public record NewCityDTO(
        String cityName,
        double longitude,
        double latitude,
        String state,
        String country
)
{ }
