package com.weather.management.weather.api;

import com.weather.management.weather.api.dto.WeatherForecastResponse;
import com.weather.management.weather.domain.*;
import com.weather.management.weather.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    private MockMvc mockMvc;

    private final LocalDate localDate = LocalDate.of(2023, 12, 1);

    private final double lat = 37.7749;
    private final double lon = -122.4194;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }

    @Test
    void testGetWeatherForecast_Success() throws Exception {
        // GIVEN
        WeatherForecastResponse mockResponse = createMockWeatherForecastResponse();
        when(weatherService.getWeatherForecast(anyDouble(), anyDouble())).thenReturn(mockResponse);

        // WHEN
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/weather/forecast")
                .param("lat", String.valueOf(lat))
                .param("lon", String.valueOf(lon))
                .accept(MediaType.APPLICATION_JSON);

        // THEN
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.source").value("tomorrow.source"))
                .andExpect(jsonPath("$.unit").value("Celsius (°C)"))
                .andExpect(jsonPath("$.timelines.hourly[0].values.temperature").value(-3.8));
    }

    private WeatherForecastResponse createMockWeatherForecastResponse() {
        var date = java.sql.Date.valueOf(localDate);

        var response = new WeatherForecastResponse();
        response.setSource("tomorrow.source");
        response.setUnit("Celsius (°C)");

        var location = new Location();
        location.setLat(lat);
        location.setLon(lon);
        response.setLocation(location);

        var values = new Values();
        values.setTemperature(-3.8);
        Hourly hourly = new Hourly(date, values);

        var timelines = new Timelines();
        timelines.setHourly(List.of(hourly));
        response.setTimelines(timelines);

        return response;
    }
}
