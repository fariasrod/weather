package com.weather.integration.accuweather.service.impl;

import com.weather.integration.accuweather.dto.AccuWeatherForecastResponse;
import com.weather.integration.accuweather.dto.AccuWeatherGeoPositionResponse;
import com.weather.integration.accuweather.service.AccuweatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AccuWeatherServiceImpl implements AccuweatherService {

    private final RestTemplate restTemplate;
    private final String host;
    private final String apiKey;

    public AccuWeatherServiceImpl(final RestTemplate restTemplate,
                                  @Value("${external-service.accuweather.host}") String host,
                                  @Value("${external-service.accuweather.apikey}") String apiKey) {
        this.restTemplate = restTemplate;
        this.host = host;
        this.apiKey = apiKey;
    }

    @Override
    public List<AccuWeatherForecastResponse> getWeatherForecast(final double lat, final double lon) {
        var accuWeatherGeoPositionResponse = this.getGeoPosition(lat, lon);
        return this.getForecasts(accuWeatherGeoPositionResponse.getKey());
    }

    @Override
    public AccuWeatherGeoPositionResponse getGeoPosition(final double lat, final double lon) {
        return restTemplate.getForObject(this.buildGeoPositionUri(lat, lon).toUriString(), AccuWeatherGeoPositionResponse.class);
    }

    @Override
    public List<AccuWeatherForecastResponse> getForecasts(final String locationKey) {
        ResponseEntity<AccuWeatherForecastResponse[]> responseEntity = restTemplate.getForEntity(
                this.buildForecastsUri(locationKey).toUriString(), AccuWeatherForecastResponse[].class);
        return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    }

    private UriComponentsBuilder buildGeoPositionUri(final double lat, final double lon) {
        var geoPositionPath = "/locations/v1/cities/geoposition/search";
        return UriComponentsBuilder.fromUriString(host + geoPositionPath)
                .queryParam("q", lat + "," + lon)
                .queryParam("apikey", apiKey);
    }

    private UriComponentsBuilder buildForecastsUri(final String locationKey) {
        var forecastsPath = "/forecasts/v1/hourly/12hour/%s";
        return UriComponentsBuilder.fromUriString(host + String.format(forecastsPath, locationKey))
                .queryParam("language", "en")
                .queryParam("metric", true)
                .queryParam("apikey", apiKey);
    }
}
