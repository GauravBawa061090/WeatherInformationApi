package com.weather.information.api.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Wind {

    private double speed;

    private double deg;

    private double gust;
}
