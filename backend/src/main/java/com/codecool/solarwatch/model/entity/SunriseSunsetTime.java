package com.codecool.solarwatch.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
public class SunriseSunsetTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    private String sunset;
    @Setter
    private String sunrise;
    private LocalDate date;

    @ManyToOne
    private City city;

    public SunriseSunsetTime() {
    }

    public SunriseSunsetTime(String sunset, String sunrise, LocalDate date, City city) {
        this.sunset = sunset;
        this.sunrise = sunrise;
        this.date = date;
        this.city = city;
    }

    public String getCityName() {
        return city.getName();
    }

}
