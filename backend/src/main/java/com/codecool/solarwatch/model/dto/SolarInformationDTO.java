package com.codecool.solarwatch.model.dto;

import java.time.LocalDate;

public record SolarInformationDTO(String cityName, LocalDate date, String sunrise, String sunset) {
}
