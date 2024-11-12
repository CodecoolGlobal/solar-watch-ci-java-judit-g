package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.exception.SunriseSunsetTimeNotFoundException;
import com.codecool.solarwatch.model.payload.CityDTO;
import com.codecool.solarwatch.model.payload.NewCityDTO;
import com.codecool.solarwatch.model.payload.NewSunriseSunsetTimeDTO;
import com.codecool.solarwatch.model.payload.SunriseSunsetTimeDTO;
import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.SunriseSunsetTime;
import com.codecool.solarwatch.model.entity.AppUser;
import com.codecool.solarwatch.model.payload.AppUserRequestDTO;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunriseSunsetTimeRepository;
import com.codecool.solarwatch.repository.AppUserRepository;
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

    private final AppUserRepository appUserRepository;
    private final SunriseSunsetTimeRepository sunriseSunsetTimeRepository;
    private final CityRepository cityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AppUserRepository appUserRepository, SunriseSunsetTimeRepository sunriseSunsetTimeRepository, CityRepository cityRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.sunriseSunsetTimeRepository = sunriseSunsetTimeRepository;
        this.cityRepository = cityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Void> createAdmin(AppUserRequestDTO adminRegistrationRequest) {
        AppUser appUser = new AppUser(adminRegistrationRequest.getUsername(), passwordEncoder.encode(adminRegistrationRequest.getPassword()), Set.of(Role.ROLE_ADMIN));
        appUserRepository.save(appUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> createSunriseSunsetTime(NewSunriseSunsetTimeDTO newSunriseSunsetTimeDTO) {
        City city = cityRepository
                .findByName(newSunriseSunsetTimeDTO.sunriseSunsetTimeCityNameDTO().cityName())
                .orElseThrow(() -> new InvalidCityException("City does not yet exist, sunrise-sunset information cannot be added. City has to be created first.")
                );

        SunriseSunsetTime sunriseSunsetTime = new SunriseSunsetTime(
                newSunriseSunsetTimeDTO.sunset(),
                newSunriseSunsetTimeDTO.sunrise(),
                newSunriseSunsetTimeDTO.date(),
                city);

        sunriseSunsetTimeRepository.save(sunriseSunsetTime);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> createCity(NewCityDTO newCityDTO) {
        City city = new City(
                newCityDTO.cityName(),
                newCityDTO.longitude(),
                newCityDTO.latitude(),
                newCityDTO.state(),
                newCityDTO.country());

        try {
            cityRepository.save(city);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataIntegrityViolationException exception) {
            throw new InvalidCityException("City with the provided name already exists");
        }
    }

    @Transactional
    public ResponseEntity<Void> updateSunriseSunsetTime(SunriseSunsetTimeDTO sunriseSunsetTimeDTO, int id) {
        SunriseSunsetTime sunriseSunsetTime = sunriseSunsetTimeRepository
                .findById((long) id)
                .orElseThrow(() -> new SunriseSunsetTimeNotFoundException("Sunrise-sunset time was not found with the provided id"));

        sunriseSunsetTime.setSunrise(sunriseSunsetTimeDTO.sunrise());
        sunriseSunsetTime.setSunset(sunriseSunsetTimeDTO.sunset());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public ResponseEntity<Void> updateCity(CityDTO cityDTO, int id) {
        City city = cityRepository
                .findById((long) id)
                .orElseThrow(() -> new InvalidCityException("City was not found with the provided id"));

        city.setName(cityDTO.cityName());
        city.setLatitude(cityDTO.latitude());
        city.setLongitude(cityDTO.longitude());
        city.setState(cityDTO.state());
        city.setCountry(cityDTO.country());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<Void> deleteSunriseSunsetTime(int id) {
        Optional<SunriseSunsetTime> sunriseSunsetTimeEntityOptional = sunriseSunsetTimeRepository.findById((long) id);

        if (sunriseSunsetTimeEntityOptional.isEmpty()) {
            throw new SunriseSunsetTimeNotFoundException("Sunrise-sunset time was not found with the provided id");
        }

        sunriseSunsetTimeRepository.deleteById((long) id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<Void> deleteCity(int id) {
        Optional<City> optionalCity = cityRepository.findById((long) id);

        if (optionalCity.isEmpty()) {
            throw new InvalidCityException("Sunrise-sunset time was not found with the provided id");
        }

        cityRepository.deleteById((long) id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
