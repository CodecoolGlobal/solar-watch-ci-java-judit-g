package com.codecool.solarwatch.service;


import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.model.CityModel;
import com.codecool.solarwatch.model.SunriseSunsetResultsModel;
import com.codecool.solarwatch.model.SunriseSunsetTimeModel;
import com.codecool.solarwatch.model.dto.SolarInformationDTO;
import com.codecool.solarwatch.model.entity.CityEntity;
import com.codecool.solarwatch.model.entity.SunriseSunsetTimeEntity;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunriseSunsetTimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SolarWatchService {

    private static final String API_KEY = "35f0a928457e4fcb24facfa47e1cf758";
    private static final Logger logger = LoggerFactory.getLogger(SolarWatchService.class);

    private final SolarWatchMapper solarWatchMapper;
    private final WebClient webClient;
    private final CityRepository cityRepository;
    private final SunriseSunsetTimeRepository sunriseSunsetTimeRepository;

    public SolarWatchService(SolarWatchMapper solarWatchMapper, WebClient webClient, CityRepository cityRepository, SunriseSunsetTimeRepository sunriseSunsetTimeRepository) {
        this.solarWatchMapper = solarWatchMapper;
        this.webClient = webClient;
        this.cityRepository = cityRepository;
        this.sunriseSunsetTimeRepository = sunriseSunsetTimeRepository;
    }

    private Optional<CityModel> fetchCityModel(String name) {
        String url = String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s", name, API_KEY);

        CityModel[] response = webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(CityModel[].class)
                .block();

        logger.info("Response from GEO Coding API: {}", response);

        if (response.length == 0) {
            return Optional.empty();
        }
        return Optional.of(response[0]);
    }

    private SunriseSunsetTimeModel fetchSunriseSunsetTime(double latitude, double longitude, LocalDate date) {
        String url = String.format("https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=%s", latitude, longitude, date);

        SunriseSunsetResultsModel response = webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(SunriseSunsetResultsModel.class)
                .block();

        logger.info("Response from Sunrise Sunset API: {}", response);

        return response.results();
    }

    private Optional<CityEntity> findCityByName(String name) {
        return cityRepository.findByName(name);
    }

    private Optional<SunriseSunsetTimeEntity> findSunriseSunsetTimeByDate(LocalDate localDate) {
        return sunriseSunsetTimeRepository.findByDate(localDate);
    }

    private SunriseSunsetTimeEntity saveSunriseSunsetTime(SunriseSunsetTimeEntity sunriseSunsetTimeEntity) {
        return sunriseSunsetTimeRepository.save(sunriseSunsetTimeEntity);
    }

    private CityEntity saveCity(CityEntity cityEntity) {
        return cityRepository.save(cityEntity);
    }

    private SunriseSunsetTimeEntity fetchAndSaveSunriseSunsetTime(CityEntity cityEntity, LocalDate date) {
        SunriseSunsetTimeModel sunriseSunsetTimeModel = fetchSunriseSunsetTime(cityEntity.getLatitude(), cityEntity.getLongitude(), date);
        SunriseSunsetTimeEntity sunriseSunsetTimeEntity = solarWatchMapper.mapToSunriseSunsetTime(sunriseSunsetTimeModel, date, cityEntity);
        return saveSunriseSunsetTime(sunriseSunsetTimeEntity);
    }

    public SolarInformationDTO provideSolarInformation(String cityName, LocalDate date) {
        Optional<CityEntity> optionalCity = findCityByName(cityName);

        if (optionalCity.isPresent()) {
            CityEntity cityEntity = optionalCity.get();
            Optional<SunriseSunsetTimeEntity> optionalSunriseSunsetTime = findSunriseSunsetTimeByDate(date);

            if (optionalSunriseSunsetTime.isPresent()) {
                SunriseSunsetTimeEntity sunriseSunsetTimeEntity = optionalSunriseSunsetTime.get();
                return solarWatchMapper.mapSunriseSunsetTimeToSolarInformationDTO(sunriseSunsetTimeEntity);
            }
            SunriseSunsetTimeEntity savedSunriseSunsetTimeEntity = fetchAndSaveSunriseSunsetTime(cityEntity, date);
            return solarWatchMapper.mapSunriseSunsetTimeToSolarInformationDTO(savedSunriseSunsetTimeEntity);
        }
        Optional<CityModel> optionalCityModel = fetchCityModel(cityName);

        if (optionalCityModel.isEmpty()) {
            throw new InvalidCityException("City with the name provided does not exist");
        }
        CityModel cityModel = optionalCityModel.get();
        CityEntity cityEntity = solarWatchMapper.mapCityModelToCity(cityModel);
        CityEntity savedCityEntity = saveCity(cityEntity);
        SunriseSunsetTimeEntity savedSunriseSunsetTimeEntity = fetchAndSaveSunriseSunsetTime(savedCityEntity, date);

        return solarWatchMapper.mapSunriseSunsetTimeToSolarInformationDTO(savedSunriseSunsetTimeEntity);
    }
}

