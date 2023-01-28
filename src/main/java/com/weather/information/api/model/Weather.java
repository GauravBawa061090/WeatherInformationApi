package com.weather.information.api.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Weather {

    private double id;

    private String main;

    private String description;

    private String icon;
}
