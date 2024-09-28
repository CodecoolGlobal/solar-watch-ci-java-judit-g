package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.dto.SolarInformationDTO;
import com.codecool.solarwatch.service.SolarWatchService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class SolarWatchControllerIT {

//I saw in resources that developers often create a service class as interface, and then an application and test implementation of it, what is the purpose of that?
//@WebMvcTest(SolarWatchController.class) => should work, but it doesnt because of security, although it should configure it by itself, why?

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SolarWatchService solarWatchService;

    @Test
    @WithMockUser(username = "user", password = "", roles = {"USER"})
    public void testGetSunriseSunsetInfoReturnsCorrectElement() throws Exception {
        Mockito.when(solarWatchService.provideSolarInformation("Balatonkenese", LocalDate.now())).thenReturn(new SolarInformationDTO("Balatonkenese", LocalDate.now(), "11:00", "12:00"));

        this.mockMvc.
                perform(MockMvcRequestBuilders.get("/api/information")
                        .param("city", "Balatonkenese")
                        .param("date", LocalDate.now().toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cityName").value("Balatonkenese"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sunrise").value("11:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sunset").value("12:00"));
    }
}
