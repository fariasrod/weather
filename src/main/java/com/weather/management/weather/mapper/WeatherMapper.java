package com.weather.management.weather.mapper;

import com.weather.integration.tomorrow.dto.TomorrowWeatherForecastResponse;
import com.weather.management.weather.api.dto.WeatherForecastResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WeatherMapper {

    WeatherMapper INSTANCE = Mappers.getMapper(WeatherMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(TomorrowWeatherForecastResponse from, @MappingTarget WeatherForecastResponse target);
}
