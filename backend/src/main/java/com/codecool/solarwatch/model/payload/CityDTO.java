package com.codecool.solarwatch.model.payload;

public record CityDTO(
        int id,
        String cityName,
        double longitude,
        double latitude,
        String state,
        String country
)
{ }
