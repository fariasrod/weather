package com.weather.management.weather.service;

import com.weather.integration.accuweather.dto.AccuWeatherForecastResponse;
import com.weather.integration.accuweather.service.AccuweatherService;
import com.weather.integration.tomorrow.dto.TomorrowWeatherForecastResponse;
import com.weather.integration.tomorrow.service.TomorrowService;
import com.weather.management.weather.service.impl.WeatherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

@SpringBootTest
class WeatherServiceTest {

    @Mock
    private TomorrowService tomorrowService;

    @Mock
    private AccuweatherService accuweatherService;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    private final LocalDate localDate = LocalDate.of(2023, 12, 1);
    private final double lat = 37.7749;
    private final double lon = -122.4194;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        weatherService = new WeatherServiceImpl(tomorrowService,
                accuweatherService,
                "tomorrow.source",
                "accuweather.source");
    }

    @DisplayName("WHEN trying to get the weather forecast THEN return TomorrowWeatherForecastResponse successfully")
    @Test
    void testGetWeatherForecast_SuccessfulCallToTomorrowIO() {
        // GIVEN
        var tomorrowIOResponse = createTomorrowIOResponse();
        when(tomorrowService.getWeatherForecast(anyDouble(), anyDouble())).thenReturn(tomorrowIOResponse);

        // WHEN
        var response = weatherService.getWeatherForecast(lat, lon);

        // THEN
        assertEquals("tomorrow.source", response.getSource());
    }

    @Test
    void testGetWeatherForecast_FallbackToAccuWeather() {
        // GIVEN
        when(tomorrowService.getWeatherForecast(anyDouble(), anyDouble()))
                .thenThrow(new RuntimeException("An error occurred while trying to access the Tomorrow IO service"));
        when(accuweatherService.getWeatherForecast(anyDouble(), anyDouble())).thenReturn(createAccuWeatherResponse());

        // THEN
        assertThrows(RuntimeException.class, () -> weatherService.getWeatherForecast(lat, lon));
    }

    private TomorrowWeatherForecastResponse createTomorrowIOResponse() {
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

    private List<AccuWeatherForecastResponse> createAccuWeatherResponse() {
        var date = java.sql.Date.valueOf(localDate);

        var response = new AccuWeatherForecastResponse();
        response.setDateTime(date);

        var temperature = new AccuWeatherForecastResponse.Temperature();
        temperature.setValue(-3.8);
        response.setTemperature(temperature);

        return List.of(response);
    }
}