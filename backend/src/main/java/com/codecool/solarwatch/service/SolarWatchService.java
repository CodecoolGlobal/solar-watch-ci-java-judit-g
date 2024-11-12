package com.codecool.solarwatch.service;


import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.model.CityModel;
import com.codecool.solarwatch.model.SunriseSunsetResultsModel;
import com.codecool.solarwatch.model.SunriseSunsetTimeModel;
import com.codecool.solarwatch.model.payload.SolarInformationDTO;
import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.entity.SunriseSunsetTime;
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

    private Optional<City> findCityByName(String name) {
        return cityRepository.findByName(name);
    }

    private Optional<SunriseSunsetTime> findSunriseSunsetTimeByDate(LocalDate localDate) {
        return sunriseSunsetTimeRepository.findByDate(localDate);
    }

    private SunriseSunsetTime saveSunriseSunsetTime(SunriseSunsetTime sunriseSunsetTime) {
        return sunriseSunsetTimeRepository.save(sunriseSunsetTime);
    }

    private City saveCity(City city) {
        return cityRepository.save(city);
    }

    private SunriseSunsetTime fetchAndSaveSunriseSunsetTime(City city, LocalDate date) {
        SunriseSunsetTimeModel sunriseSunsetTimeModel = fetchSunriseSunsetTime(city.getLatitude(), city.getLongitude(), date);
        SunriseSunsetTime sunriseSunsetTime = solarWatchMapper.mapToSunriseSunsetTime(sunriseSunsetTimeModel, date, city);
        return saveSunriseSunsetTime(sunriseSunsetTime);
    }

    public SolarInformationDTO provideSolarInformation(String cityName, LocalDate date) {
        Optional<City> optionalCity = findCityByName(cityName);

        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            Optional<SunriseSunsetTime> optionalSunriseSunsetTime = findSunriseSunsetTimeByDate(date);

            if (optionalSunriseSunsetTime.isPresent()) {
                SunriseSunsetTime sunriseSunsetTime = optionalSunriseSunsetTime.get();
                return solarWatchMapper.mapSunriseSunsetTimeToSolarInformationDTO(sunriseSunsetTime);
            }
            SunriseSunsetTime savedSunriseSunsetTime = fetchAndSaveSunriseSunsetTime(city, date);
            return solarWatchMapper.mapSunriseSunsetTimeToSolarInformationDTO(savedSunriseSunsetTime);
        }
        Optional<CityModel> optionalCityModel = fetchCityModel(cityName);

        if (optionalCityModel.isEmpty()) {
            throw new InvalidCityException("City with the name provided does not exist");
        }
        CityModel cityModel = optionalCityModel.get();
        City city = solarWatchMapper.mapCityModelToCity(cityModel);
        City savedCity = saveCity(city);
        SunriseSunsetTime savedSunriseSunsetTime = fetchAndSaveSunriseSunsetTime(savedCity, date);

        return solarWatchMapper.mapSunriseSunsetTimeToSolarInformationDTO(savedSunriseSunsetTime);
    }
}

