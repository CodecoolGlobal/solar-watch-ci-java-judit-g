package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.entity.SunriseSunsetTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SunriseSunsetTimeRepository extends JpaRepository<SunriseSunsetTime, Long> {

    Optional<SunriseSunsetTime> findByDate(LocalDate localDate);
}
