package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.entity.SunriseSunsetTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SunriseSunsetTimeRepository extends JpaRepository<SunriseSunsetTimeEntity, Long> {

    Optional<SunriseSunsetTimeEntity> findByDate(LocalDate localDate);
}
