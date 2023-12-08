package com.weather.integration.accuweather.service;

import com.weather.integration.accuweather.dto.AccuWeatherForecastResponse;
import com.weather.integration.accuweather.dto.AccuWeatherGeoPositionResponse;

import java.util.List;

public interface AccuweatherService {

    List<AccuWeatherForecastResponse> getWeatherForecast(double lat, double lon);

    AccuWeatherGeoPositionResponse getGeoPosition(double lat, double lon);

    List<AccuWeatherForecastResponse> getForecasts(String locationKey);
}
