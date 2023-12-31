package com.weather.management.weather.api.dto;

import com.weather.management.weather.domain.Location;
import com.weather.management.weather.domain.Timelines;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecastResponse {
    private LocalDateTime requisitionEnquiryDate = LocalDateTime.now();
    private Timelines timelines;
    private Location location;
    private String source;
    private String unit = "Celsius (°C)";
}
