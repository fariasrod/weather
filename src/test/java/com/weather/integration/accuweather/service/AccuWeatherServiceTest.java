package com.weather.integration.accuweather.service;

import com.weather.integration.accuweather.dto.AccuWeatherForecastResponse;
import com.weather.integration.accuweather.dto.AccuWeatherGeoPositionResponse;
import com.weather.integration.accuweather.service.impl.AccuWeatherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccuWeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private AccuWeatherServiceImpl accuweatherService;
    private final LocalDate localDate = LocalDate.of(2023, 12, 1);
    private final double lat = 37.7749;
    private final double lon = -122.4194;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        accuweatherService = new AccuWeatherServiceImpl(restTemplate, "https://example", "API_KEY");
    }

    @Test
    void testGetGeoPosition_SuccessfulCall() {
        // GIVEN
        var mockResponse = createGeoPositionMockResponse();
        when(restTemplate.getForObject(any(String.class), any(Class.class))).thenReturn(mockResponse);

        // WHEN
        var response = accuweatherService.getGeoPosition(lat, lon);

        // THEN
        assertEquals(mockResponse, response);
    }

    @Test
    void testGetGeoPosition_ApiError() {
        // GIVEN
        when(restTemplate.getForObject(any(String.class), any(Class.class)))
                .thenThrow(new RestClientException("Simulated API error"));

        // THEN
        assertThrows(RestClientException.class,
                () -> accuweatherService.getGeoPosition(lat, lon));
    }

    @Test
    void testGetForecasts_SuccessfulCall() {
        // GIVEN
        AccuWeatherForecastResponse[] mockResponse = createMockForecastResponseArray();
        ResponseEntity<AccuWeatherForecastResponse[]> responseEntity = ResponseEntity.ok(mockResponse);
        when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenReturn(responseEntity);

        // WHEN
        var response = accuweatherService.getForecasts("KEY");

        // THEN
        assertEquals(Arrays.asList(mockResponse), response);
        assertEquals(Arrays.asList(mockResponse).size(), response.size());
    }

    @Test
    void testGetForecasts_ApiError() {
        // GIVEN
        when(restTemplate.getForEntity(any(String.class), any(Class.class)))
                .thenThrow(new RestClientException("Simulated API error"));

        // THEN
        assertThrows(RestClientException.class,
                () -> accuweatherService.getForecasts("KEY"));
    }

    private AccuWeatherGeoPositionResponse createGeoPositionMockResponse() {
        var response = new AccuWeatherGeoPositionResponse();
        response.setKey("KEY");
        return response;
    }

    private AccuWeatherForecastResponse[] createMockForecastResponseArray() {
        var date = java.sql.Date.valueOf(localDate);

        var response = new AccuWeatherForecastResponse();
        response.setDateTime(date);

        var temperature = new AccuWeatherForecastResponse.Temperature();
        temperature.setValue(-3.8);
        response.setTemperature(temperature);
        return new AccuWeatherForecastResponse[]{response};

    }
}
