package com.codecool.solarwatch.model.dto;

public record CityDTO(
        int id,
        String cityName,
        double longitude,
        double latitude,
        String state,
        String country
)
{ }
