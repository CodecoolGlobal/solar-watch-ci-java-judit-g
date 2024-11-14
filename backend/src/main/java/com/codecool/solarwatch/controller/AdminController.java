package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.payload.*;
import com.codecool.solarwatch.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createAdmin(@RequestBody AppUserRequestDTO appUserRequestDTO) {
        return adminService.createAdmin(appUserRequestDTO);
    }

    @PostMapping("/create/sunrise-sunset-time")
    public ResponseEntity<Void> createSunriseSunsetTime(@RequestBody NewSunriseSunsetTimeDTO newSunriseSunsetTimeDTO) {
        return adminService.createSunriseSunsetTime(newSunriseSunsetTimeDTO);
    }

    @PostMapping("/create/city")
    public ResponseEntity<Void> createCity(@RequestBody NewCityDTO newCityDTO) {
        return adminService.createCity(newCityDTO);
    }

    @PutMapping("/update/sunrise-sunset-time/{id}")
    public ResponseEntity<Void> updateSunriseSunsetTime(@RequestBody SunriseSunsetTimeDTO sunriseSunsetTimeDTO, @PathVariable int id) {
        return adminService.updateSunriseSunsetTime(sunriseSunsetTimeDTO, id);
    }

    @PutMapping("/update/city/{id}")
    public ResponseEntity<Void> updateCity(@RequestBody CityDTO cityDTO, @PathVariable int id) {
        return adminService.updateCity(cityDTO, id);
    }

    @DeleteMapping("/delete/sunrise-sunset-time/{id}")
    public ResponseEntity<Void> deleteSunriseSunsetTime(@PathVariable int id) {
        return adminService.deleteSunriseSunsetTime(id);
    }

    @DeleteMapping("/delete/city/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable int id) {
        return adminService.deleteCity(id);
    }
}
