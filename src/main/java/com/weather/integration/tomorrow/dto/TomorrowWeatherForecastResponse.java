package com.weather.integration.tomorrow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TomorrowWeatherForecastResponse {
    private Timelines timelines;
    private Location location;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Timelines {
        private List<Minutely> minutely = new ArrayList<>();
        private List<Hourly> hourly = new ArrayList<>();
        private List<Daily> daily = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {
        private double lat;
        private double lon;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Daily {
        private Date time;
        private Values values;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Hourly {
        private Date time;
        private Values values;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Minutely {
        private Date time;
        private Values values;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Values {
        private double cloudBase;
        private double cloudCeiling;
        private double cloudCover;
        private double dewPoint;
        private int freezingRainIntensity;
        private double humidity;
        private int precipitationProbability;
        private double pressureSurfaceLevel;
        private int rainIntensity;
        private int sleetIntensity;
        private int snowIntensity;
        private double temperature;
        private double temperatureApparent;
        private int uvHealthConcern;
        private int uvIndex;
        private int visibility;
        private int weatherCode;
        private double windDirection;
        private double windGust;
        private double windSpeed;
        private double evapotranspiration;
        private int iceAccumulation;
        private int iceAccumulationLwe;
        private int rainAccumulation;
        private int rainAccumulationLwe;
        private int sleetAccumulation;
        private int sleetAccumulationLwe;
        private double snowAccumulation;
        private int snowAccumulationLwe;
        private double snowDepth;
        private double cloudBaseAvg;
        private double cloudBaseMax;
        private int cloudBaseMin;
        private double cloudCeilingAvg;
        private double cloudCeilingMax;
        private int cloudCeilingMin;
        private double cloudCoverAvg;
        private double cloudCoverMax;
        private double cloudCoverMin;
        private double dewPointAvg;
        private double dewPointMax;
        private double dewPointMin;
        private double evapotranspirationAvg;
        private double evapotranspirationMax;
        private double evapotranspirationMin;
        private double evapotranspirationSum;
        private int freezingRainIntensityAvg;
        private int freezingRainIntensityMax;
        private int freezingRainIntensityMin;
        private double humidityAvg;
        private double humidityMax;
        private double humidityMin;
        private int iceAccumulationAvg;
        private int iceAccumulationLweAvg;
        private int iceAccumulationLweMax;
        private int iceAccumulationLweMin;
        private int iceAccumulationLweSum;
        private int iceAccumulationMax;
        private int iceAccumulationMin;
        private int iceAccumulationSum;
        private Date moonriseTime;
        private Date moonsetTime;
        private int precipitationProbabilityAvg;
        private int precipitationProbabilityMax;
        private int precipitationProbabilityMin;
        private double pressureSurfaceLevelAvg;
        private double pressureSurfaceLevelMax;
        private double pressureSurfaceLevelMin;
        private int rainAccumulationAvg;
        private int rainAccumulationLweAvg;
        private int rainAccumulationLweMax;
        private int rainAccumulationLweMin;
        private int rainAccumulationMax;
        private int rainAccumulationMin;
        private int rainAccumulationSum;
        private int rainIntensityAvg;
        private int rainIntensityMax;
        private int rainIntensityMin;
        private int sleetAccumulationAvg;
        private int sleetAccumulationLweAvg;
        private int sleetAccumulationLweMax;
        private int sleetAccumulationLweMin;
        private int sleetAccumulationLweSum;
        private int sleetAccumulationMax;
        private int sleetAccumulationMin;
        private int sleetIntensityAvg;
        private int sleetIntensityMax;
        private int sleetIntensityMin;
        private double snowAccumulationAvg;
        private int snowAccumulationLweAvg;
        private int snowAccumulationLweMax;
        private int snowAccumulationLweMin;
        private int snowAccumulationLweSum;
        private double snowAccumulationMax;
        private int snowAccumulationMin;
        private double snowAccumulationSum;
        private double snowDepthAvg;
        private double snowDepthMax;
        private int snowDepthMin;
        private double snowDepthSum;
        private int snowIntensityAvg;
        private int snowIntensityMax;
        private int snowIntensityMin;
        private Date sunriseTime;
        private Date sunsetTime;
        private double temperatureApparentAvg;
        private double temperatureApparentMax;
        private double temperatureApparentMin;
        private double temperatureAvg;
        private double temperatureMax;
        private double temperatureMin;
        private int uvHealthConcernAvg;
        private int uvHealthConcernMax;
        private int uvHealthConcernMin;
        private int uvIndexAvg;
        private int uvIndexMax;
        private int uvIndexMin;
        private double visibilityAvg;
        private double visibilityMax;
        private double visibilityMin;
        private int weatherCodeMax;
        private int weatherCodeMin;
        private double windDirectionAvg;
        private double windGustAvg;
        private double windGustMax;
        private double windGustMin;
        private double windSpeedAvg;
        private double windSpeedMax;
        private double windSpeedMin;
    }
}
