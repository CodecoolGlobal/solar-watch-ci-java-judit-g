package com.codecool.solarwatch.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column(unique = true)
    private String name;

    @Setter
    private double longitude;

    @Setter
    private double latitude;

    @Setter
    private String state;

    @Setter
    private String country;

    @Setter
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<SunriseSunsetTime> sunriseSunsetTimes;

    public City() {
    }

    public City(String name, double longitude, double latitude, String state, String country) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.state = state;
        this.country = country;
    }
}
