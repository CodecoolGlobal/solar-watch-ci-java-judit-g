package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.CityModel;
import com.codecool.solarwatch.model.SunriseSunsetTimeModel;
import com.codecool.solarwatch.model.payload.SolarInformationDTO;
import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.entity.SunriseSunsetTime;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SolarWatchMapper {

    public SolarInformationDTO mapSunriseSunsetTimeToSolarInformationDTO(SunriseSunsetTime sunriseSunsetTime) {
        return new SolarInformationDTO(
                sunriseSunsetTime.getCityName(),
                sunriseSunsetTime.getDate(),
                sunriseSunsetTime.getSunrise(),
                sunriseSunsetTime.getSunset()
        );
    }

    public City mapCityModelToCity(CityModel cityModel) {
        return new City(
                cityModel.name(),
                cityModel.lon(),
                cityModel.lat(),
                cityModel.state(),
                cityModel.country()
        );
    }

    public SunriseSunsetTime mapToSunriseSunsetTime(SunriseSunsetTimeModel sunriseSunsetTimeModel, LocalDate localDate, City city) {
        return new SunriseSunsetTime(
                sunriseSunsetTimeModel.sunset(),
                sunriseSunsetTimeModel.sunrise(),
                localDate,
                city
        );
    }
}
