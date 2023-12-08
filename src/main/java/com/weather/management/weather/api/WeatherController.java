package com.weather.management.weather.api;

import com.weather.management.weather.domain.WeatherForecastResponse;
import com.weather.management.weather.service.WeatherService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "weather/forecast", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public WeatherForecastResponse getWeatherForecast(@RequestParam double lat, @RequestParam double lon) {
        return weatherService.getWeatherForecast(lat, lon);
    }
}
