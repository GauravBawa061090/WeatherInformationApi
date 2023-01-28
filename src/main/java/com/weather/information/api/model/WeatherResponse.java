package com.weather.information.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class WeatherResponse {

    private Coord coord;

    private List<Weather> weather;

    private String base;

    private Main main;

    private String visibility;

    private Wind wind;

    private Clouds clouds;

    private double dt;

    private Sys sys;

    private double timezone;

    private double id;

    private String name;

    private String cod;

    private String message;

}
