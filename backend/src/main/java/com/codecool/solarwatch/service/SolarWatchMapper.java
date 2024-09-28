package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.CityModel;
import com.codecool.solarwatch.model.SunriseSunsetTimeModel;
import com.codecool.solarwatch.model.dto.SolarInformationDTO;
import com.codecool.solarwatch.model.entity.CityEntity;
import com.codecool.solarwatch.model.entity.SunriseSunsetTimeEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SolarWatchMapper {

    public SolarInformationDTO mapSunriseSunsetTimeToSolarInformationDTO(SunriseSunsetTimeEntity sunriseSunsetTimeEntity) {
        return new SolarInformationDTO(
                sunriseSunsetTimeEntity.getCityName(),
                sunriseSunsetTimeEntity.getDate(),
                sunriseSunsetTimeEntity.getSunrise(),
                sunriseSunsetTimeEntity.getSunset()
        );
    }

    public CityEntity mapCityModelToCity(CityModel cityModel) {
        return new CityEntity(
                cityModel.name(),
                cityModel.lon(),
                cityModel.lat(),
                cityModel.state(),
                cityModel.country()
        );
    }

    public SunriseSunsetTimeEntity mapToSunriseSunsetTime(SunriseSunsetTimeModel sunriseSunsetTimeModel, LocalDate localDate, CityEntity cityEntity) {
        return new SunriseSunsetTimeEntity(
                sunriseSunsetTimeModel.sunset(),
                sunriseSunsetTimeModel.sunrise(),
                localDate,
                cityEntity
        );
    }
}
