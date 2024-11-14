package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.model.payload.SolarInformationDTO;
import com.codecool.solarwatch.service.SolarWatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/information")
public class SolarWatchController {

    private final SolarWatchService solarWatchService;

    public SolarWatchController(SolarWatchService solarWatchService) {
        this.solarWatchService = solarWatchService;
    }

    @GetMapping
    public ResponseEntity<SolarInformationDTO> getSunriseSunsetInfo(@RequestParam String city, @RequestParam LocalDate date) {
        try {
            SolarInformationDTO solarInfo = solarWatchService.provideSolarInformation(city, date);
            return ResponseEntity.ok(solarInfo);
        } catch (InvalidCityException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
