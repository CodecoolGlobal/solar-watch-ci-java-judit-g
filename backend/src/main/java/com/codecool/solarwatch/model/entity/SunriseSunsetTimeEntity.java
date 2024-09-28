package com.codecool.solarwatch.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
public class SunriseSunsetTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    @Setter
    private String sunset;
    @Setter
    private String sunrise;
    private LocalDate date;

    @ManyToOne
    private CityEntity cityEntity;

    public SunriseSunsetTimeEntity() {
    }

    public SunriseSunsetTimeEntity(String sunset, String sunrise, LocalDate date, CityEntity cityEntity) {
        this.sunset = sunset;
        this.sunrise = sunrise;
        this.date = date;
        this.cityEntity = cityEntity;
    }

    public String getCityName() {
        return cityEntity.getName();
    }

}
