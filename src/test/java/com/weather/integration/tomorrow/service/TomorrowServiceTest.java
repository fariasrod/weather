package com.weather.integration.tomorrow.service;

import com.weather.integration.tomorrow.dto.TomorrowWeatherForecastResponse;
import com.weather.integration.tomorrow.service.impl.TomorrowServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TomorrowServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private TomorrowServiceImpl tomorrowService;
    private final LocalDate localDate = LocalDate.of(2023, 12, 1);
    private final double lat = 37.7749;
    private final double lon = -122.4194;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tomorrowService = new TomorrowServiceImpl(restTemplate, "https://example", "API_KEY");
    }

    @Test
    void testGetWeatherForecast_SuccessfulCall() {
        // GIVEN
        var mockResponse = createMockResponse();
        when(restTemplate.getForObject(any(String.class), any(Class.class))).thenReturn(mockResponse);

        // WHEN
        var response = tomorrowService.getWeatherForecast(lat, lon);

        // THEN
        assertEquals(mockResponse, response);
    }

    @Test
    void testGetWeatherForecast_ApiError() {
        // GIVEN
        when(restTemplate.getForObject(any(String.class), any(Class.class)))
                .thenThrow(new RestClientException("Simulated API error"));

        // THEN
        assertThrows(RestClientException.class,
                () -> tomorrowService.getWeatherForecast(lat, lon));
    }

    private TomorrowWeatherForecastResponse createMockResponse() {
        var date = java.sql.Date.valueOf(localDate);

        var values = new TomorrowWeatherForecastResponse.Values();
        values.setTemperature(-3.8);
        TomorrowWeatherForecastResponse.Hourly hourly = new TomorrowWeatherForecastResponse.Hourly(date, values);

        var timelines = new TomorrowWeatherForecastResponse.Timelines();
        timelines.setHourly(List.of(hourly));

        var response = new TomorrowWeatherForecastResponse();
        response.setLocation(new TomorrowWeatherForecastResponse.Location(37.7749, -122.4194));
        response.setTimelines(timelines);
        return response;
    }
}
