package com.weather.integration.accuweather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccuWeatherForecastResponse {
    @JsonProperty("DateTime")
    private Date dateTime;
    @JsonProperty("EpochDateTime")
    private int epochDateTime;
    @JsonProperty("WeatherIcon")
    private int weatherIcon;
    @JsonProperty("IconPhrase")
    private String iconPhrase;
    @JsonProperty("HasPrecipitation")
    private boolean hasPrecipitation;
    @JsonProperty("IsDaylight")
    private boolean isDaylight;
    @JsonProperty("Temperature")
    private Temperature temperature;
    @JsonProperty("PrecipitationProbability")
    private int precipitationProbability;
    @JsonProperty("MobileLink")
    private String mobileLink;
    @JsonProperty("Link")
    private String link;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Temperature {
        @JsonProperty("Value")
        private double value;
        @JsonProperty("Unit")
        private String unit;
        @JsonProperty("UnitType")
        private int unitType;
    }
}
