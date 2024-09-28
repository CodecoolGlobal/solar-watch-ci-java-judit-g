package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.dto.CityDTO;
import com.codecool.solarwatch.model.dto.NewCityDTO;
import com.codecool.solarwatch.model.dto.NewSunriseSunsetTimeDTO;
import com.codecool.solarwatch.model.dto.SunriseSunsetTimeDTO;
import com.codecool.solarwatch.model.payload.UserRequest;
import com.codecool.solarwatch.repository.SunriseSunsetTimeRepository;
import com.codecool.solarwatch.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService, SunriseSunsetTimeRepository sunriseSunsetTimeRepository) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createAdmin(@RequestBody UserRequest userRequest) {
        return adminService.createAdmin(userRequest);
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
