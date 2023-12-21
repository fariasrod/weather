package com.weather.management.weather.service;

import com.weather.management.weather.api.dto.WeatherForecastResponse;

public interface WeatherService {
    WeatherForecastResponse getWeatherForecast(double lat, double lon);
}
