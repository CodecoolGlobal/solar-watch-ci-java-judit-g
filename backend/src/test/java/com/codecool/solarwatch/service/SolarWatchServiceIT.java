package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.CityModel;
import com.codecool.solarwatch.model.SunriseSunsetResultsModel;
import com.codecool.solarwatch.model.SunriseSunsetTimeModel;
import com.codecool.solarwatch.model.dto.SolarInformationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class SolarWatchServiceIT {

    private final SolarWatchService solarWatchService;

    @Autowired
    public SolarWatchServiceIT(SolarWatchService solarWatchService) {
        this.solarWatchService = solarWatchService;
    }

    @MockBean
    private WebClient webClient;

    private LocalDate testDate;

    @BeforeEach
    public void setUp() {
        testDate = LocalDate.now();
    }

    @Test
    public void whenValidCityName_thenSolarInformationShouldBeFound() {
        CityModel[] cityModel = {new CityModel("TestCity", 12.34, 56.78, "TestState", "TestCountry")};
        SunriseSunsetResultsModel resultsModel = new SunriseSunsetResultsModel(new SunriseSunsetTimeModel("06:00", "18:00"));

        final var uriSpecMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        final var requestHeadersSpecMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        final var responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);

        Mockito.when(webClient.get()).thenReturn(uriSpecMock);
        Mockito.when(uriSpecMock.uri(ArgumentMatchers.<String>notNull())).thenReturn(requestHeadersSpecMock);
        Mockito.when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToMono(CityModel[].class)).thenReturn(Mono.just(cityModel));

        Mockito.when(webClient.get()).thenReturn(uriSpecMock);
        Mockito.when(uriSpecMock.uri(ArgumentMatchers.<String>notNull())).thenReturn(requestHeadersSpecMock);
        Mockito.when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToMono(SunriseSunsetResultsModel.class)).thenReturn(Mono.just(resultsModel));

        SolarInformationDTO solarInformation = solarWatchService.provideSolarInformation("TestCity", testDate);

        assertThat(solarInformation.sunrise()).isEqualTo("06:00");
        assertThat(solarInformation.sunset()).isEqualTo("18:00");
        assertThat(solarInformation.cityName()).isEqualTo("TestCity");
    }
}
