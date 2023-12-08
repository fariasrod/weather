package com.weather.integration.tomorrow.service.impl;

import com.weather.integration.tomorrow.dto.TomorrowWeatherForecastResponse;
import com.weather.integration.tomorrow.service.TomorrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class TomorrowServiceImpl implements TomorrowService {

    private final RestTemplate restTemplate;
    private final String host;
    private final String apiKey;

    public TomorrowServiceImpl(final RestTemplate restTemplate,
                               @Value("${external-service.tomorrow.host}") String host,
                               @Value("${external-service.tomorrow.apikey}") String apiKey) {
        this.restTemplate = restTemplate;
        this.host = host;
        this.apiKey = apiKey;
    }

    @Override
    public TomorrowWeatherForecastResponse getWeatherForecast(final double lat, final double lon) {
        return restTemplate.getForObject(this.buildUri(lat, lon).toUriString(), TomorrowWeatherForecastResponse.class);
    }

    private UriComponentsBuilder buildUri(final double lat, final double lon) {
        return UriComponentsBuilder.fromUriString(host)
                .queryParam("location", lat + "," + lon)
                .queryParam("fields", "temperature_2m")
                .queryParam("units", "metric")
                .queryParam("apikey", apiKey);
    }
}
