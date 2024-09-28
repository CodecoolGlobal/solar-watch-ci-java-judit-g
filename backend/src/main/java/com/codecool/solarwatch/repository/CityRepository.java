package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    Optional<CityEntity> findByName(String name);


}
