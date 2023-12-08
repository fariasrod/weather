package com.weather.management.weather.service;

import com.weather.management.weather.domain.WeatherForecastResponse;

public interface WeatherService {
    WeatherForecastResponse getWeatherForecast(double lat, double lon);
}
