package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.exception.SunriseSunsetTimeNotFoundException;
import com.codecool.solarwatch.model.dto.CityDTO;
import com.codecool.solarwatch.model.dto.NewCityDTO;
import com.codecool.solarwatch.model.dto.NewSunriseSunsetTimeDTO;
import com.codecool.solarwatch.model.dto.SunriseSunsetTimeDTO;
import com.codecool.solarwatch.model.entity.CityEntity;
import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.SunriseSunsetTimeEntity;
import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.model.payload.UserRequest;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunriseSunsetTimeRepository;
import com.codecool.solarwatch.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final SunriseSunsetTimeRepository sunriseSunsetTimeRepository;
    private final CityRepository cityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(UserRepository userRepository, SunriseSunsetTimeRepository sunriseSunsetTimeRepository, CityRepository cityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sunriseSunsetTimeRepository = sunriseSunsetTimeRepository;
        this.cityRepository = cityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Void> createAdmin(UserRequest adminRegistrationRequest) {
        UserEntity userEntity = new UserEntity(adminRegistrationRequest.getUsername(), passwordEncoder.encode(adminRegistrationRequest.getPassword()), Set.of(Role.ROLE_ADMIN));
        userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> createSunriseSunsetTime(NewSunriseSunsetTimeDTO newSunriseSunsetTimeDTO) {
        CityEntity cityEntity = cityRepository
                .findByName(newSunriseSunsetTimeDTO.sunriseSunsetTimeCityNameDTO().cityName())
                .orElseThrow(() -> new InvalidCityException("City does not yet exist, sunrise-sunset information cannot be added. City has to be created first.")
                );

        SunriseSunsetTimeEntity sunriseSunsetTimeEntity = new SunriseSunsetTimeEntity(
                newSunriseSunsetTimeDTO.sunset(),
                newSunriseSunsetTimeDTO.sunrise(),
                newSunriseSunsetTimeDTO.date(),
                cityEntity);

        sunriseSunsetTimeRepository.save(sunriseSunsetTimeEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> createCity(NewCityDTO newCityDTO) {
        CityEntity cityEntity = new CityEntity(
                newCityDTO.cityName(),
                newCityDTO.longitude(),
                newCityDTO.latitude(),
                newCityDTO.state(),
                newCityDTO.country());

        try {
            cityRepository.save(cityEntity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataIntegrityViolationException exception) {
            throw new InvalidCityException("City with the provided name already exists");
        }
    }

    @Transactional
    public ResponseEntity<Void> updateSunriseSunsetTime(SunriseSunsetTimeDTO sunriseSunsetTimeDTO, int id) {
        SunriseSunsetTimeEntity sunriseSunsetTimeEntity = sunriseSunsetTimeRepository
                .findById((long) id)
                .orElseThrow(() -> new SunriseSunsetTimeNotFoundException("Sunrise-sunset time was not found with the provided id"));

        sunriseSunsetTimeEntity.setSunrise(sunriseSunsetTimeDTO.sunrise());
        sunriseSunsetTimeEntity.setSunset(sunriseSunsetTimeDTO.sunset());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public ResponseEntity<Void> updateCity(CityDTO cityDTO, int id) {
        CityEntity cityEntity = cityRepository
                .findById((long) id)
                .orElseThrow(() -> new InvalidCityException("City was not found with the provided id"));

        cityEntity.setName(cityDTO.cityName());
        cityEntity.setLatitude(cityDTO.latitude());
        cityEntity.setLongitude(cityDTO.longitude());
        cityEntity.setState(cityDTO.state());
        cityEntity.setCountry(cityDTO.country());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<Void> deleteSunriseSunsetTime(int id) {
        Optional<SunriseSunsetTimeEntity> sunriseSunsetTimeEntityOptional = sunriseSunsetTimeRepository.findById((long) id);

        if (sunriseSunsetTimeEntityOptional.isEmpty()) {
            throw new SunriseSunsetTimeNotFoundException("Sunrise-sunset time was not found with the provided id");
        }

        sunriseSunsetTimeRepository.deleteById((long) id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<Void> deleteCity(int id) {
        Optional<CityEntity> optionalCity = cityRepository.findById((long) id);

        if (optionalCity.isEmpty()) {
            throw new InvalidCityException("Sunrise-sunset time was not found with the provided id");
        }

        cityRepository.deleteById((long) id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
