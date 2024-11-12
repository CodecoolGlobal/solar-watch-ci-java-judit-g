package com.codecool.solarwatch.model.payload;

import java.time.LocalDate;

public record SolarInformationDTO(String cityName, LocalDate date, String sunrise, String sunset) {
}
