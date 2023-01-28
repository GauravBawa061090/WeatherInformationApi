package com.weather.information.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Main {

    private double main;

    @JsonProperty("feels_like")
    private double feelsLike;

    @JsonProperty("temp_min")
    private double tempMin;

    @JsonProperty("temp_max")
    private double tempMax;

    private double pressure;

    private double humidity;

    @JsonProperty("sea_level")
    private double seaLevel;

    @JsonProperty("grnd_level")
    private double grndLevel;
}
