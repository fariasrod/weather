package com.weather.integration.tomorrow.service;

import com.weather.integration.tomorrow.dto.TomorrowWeatherForecastResponse;

public interface TomorrowService {
    TomorrowWeatherForecastResponse getWeatherForecast(double lat, double lon);
}
