package com.weather.information.api.resource;

import com.weather.information.api.model.WeatherResponse;
import com.weather.information.api.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WeatherResource {

    @Autowired
    private WeatherService service;

    @GetMapping("/location")
    public WeatherResponse getWeatherReportByLocation(@RequestParam Map<String, String> location) {
        return service.getWeatherReportByLocation(location);
    }

    @GetMapping("/coordinate")
    public WeatherResponse getWeatherReportByGeoCoordinates(@RequestParam Map<String, String> location) {
        return service.getWeatherReportByGeoCoordinates(location);
    }
}
