package com.weather.management.weather.service.impl;

import com.weather.integration.accuweather.dto.AccuWeatherForecastResponse;
import com.weather.integration.accuweather.service.AccuweatherService;
import com.weather.integration.tomorrow.dto.TomorrowWeatherForecastResponse;
import com.weather.integration.tomorrow.service.TomorrowService;
import com.weather.management.weather.domain.*;
import com.weather.management.weather.mapper.WeatherMapper;
import com.weather.management.weather.service.WeatherService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {

    private final TomorrowService tomorrowService;
    private final AccuweatherService accuweatherService;
    private final String tomorrowSource;
    private final String accuweatherSource;
    private double lastLat;
    private double lastLon;

    public WeatherServiceImpl(final TomorrowService tomorrowService,
                              final AccuweatherService accuweatherService,
                              @Value("${external-service.tomorrow.source}") String tomorrowSource,
                              @Value("${external-service.accuweather.source}") String accuweatherSource) {
        this.tomorrowService = tomorrowService;
        this.accuweatherService = accuweatherService;
        this.tomorrowSource = tomorrowSource;
        this.accuweatherSource = accuweatherSource;
    }

    @CircuitBreaker(name = "weatherService", fallbackMethod = "fallbackAfterRetry")
    @Retry(name = "weatherServiceRetry", fallbackMethod = "fallbackAfterRetry")
    @Override
    public WeatherForecastResponse getWeatherForecast(final double lat, final double lon) {
        log.info(String.format("Trying to access an external service { Tomorrow IO } for lat %s and lon %s", lat, lon));
        lastLat = lat;
        lastLon = lon;

        try {
            var response = tomorrowService.getWeatherForecast(lat, lon);
            return this.mergeAndReturnWeatherForecastResponse(response);
        } catch (Exception e) {
            log.error("An error occurred while trying to access the Tomorrow IO service");
            throw new RuntimeException("An error occurred while trying to access the Tomorrow IO service", e);
        }
    }

    public WeatherForecastResponse fallbackAfterRetry(Exception e) {
        log.error("Fallback called, due to error : ", e);
        log.info(String.format("Trying to access an external service { AccuWeather } for lat %s and lon %s", lastLat, lastLon));
        try {
            return this.mergeAndReturnWeatherForecastResponse(accuweatherService.getWeatherForecast(lastLat, lastLon));
        } catch (Exception ex) {
            log.error("An error occurred while trying to access the AccuWeather service", ex);
            return new WeatherForecastResponse();
        }

    }

    public WeatherForecastResponse mergeAndReturnWeatherForecastResponse(TomorrowWeatherForecastResponse response) {
        var weatherForecastResponse = new WeatherForecastResponse();
        WeatherMapper.INSTANCE.merge(response, weatherForecastResponse);
        weatherForecastResponse.setSource(tomorrowSource);
        return weatherForecastResponse;
    }

    public WeatherForecastResponse mergeAndReturnWeatherForecastResponse(List<AccuWeatherForecastResponse> response) {
        var weatherForecastResponse = new WeatherForecastResponse();
        weatherForecastResponse.setSource(accuweatherSource);

        Location location = new Location();
        location.setLat(lastLat);
        location.setLon(lastLon);
        weatherForecastResponse.setLocation(location);

        var timelines = new Timelines();

        for (AccuWeatherForecastResponse r : response) {
            var hourly = new Hourly();
            hourly.setTime(r.getDateTime());

            var values = new Values();
            values.setTemperature(r.getTemperature().getValue());
            hourly.setValues(values);
            timelines.getHourly().add(hourly);
        }
        weatherForecastResponse.setTimelines(timelines);
        return weatherForecastResponse;
    }
}
