package com.weather.management.weather.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hourly {
    private Date time;
    private Values values;
}
