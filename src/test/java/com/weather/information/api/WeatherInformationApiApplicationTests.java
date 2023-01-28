package com.weather.information.api;

import com.weather.information.api.model.WeatherResponse;
import com.weather.information.api.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.weather.information.api.constants.WeatherConstantsIfc.*;
import static org.junit.Assert.assertTrue;

@SpringBootTest
class WeatherInformationApiApplicationTests {

    @Value("${weather.info.url}")
    private String url;

    @Value("${weather.info.api.key}")
    private String apiKey;

    @Autowired
    private WeatherService service;

    @Test
    void contextLoads() {
        assertTrue(Objects.nonNull(url) && Objects.nonNull(apiKey) && url.equals("https://api.openweathermap.org/data/2.5/weather"));
    }

    @Test
    public void getWeatherReportByLocationTest_City() {
        Map<String, String> location = new HashMap<>();
        location.put(CITY, "London");
        WeatherResponse response = service.queryByLocation(url, apiKey, location).getBody();
        assertTrue(Objects.nonNull(response));
    }

    @Test
    public void getWeatherReportByLocationTest_Country() {
        Map<String, String> location = new HashMap<>();
        location.put(CITY, "India");
        WeatherResponse response = service.getWeatherReportByLocation(location);
        assertTrue(Objects.nonNull(response));
    }

    @Test
    public void getWeatherReportByLocationTest_Error() {
        Map<String, String> location = new HashMap<>();
        WeatherResponse response = service.getWeatherReportByLocation(location);
        assertTrue(Objects.nonNull(response) && response.getMessage().equals("INVALID REQUEST ATTRIBUTES. PLEASE PASS region"));
    }

    @Test
    public void getWeatherReportByGeoCoordinatesTest() {
        Map<String, String> location = new HashMap<>();
        location.put(LATITUDE, "1.58");
        location.put(LONGITUDE, "-0.1257");
        WeatherResponse response = service.getWeatherReportByGeoCoordinates(location);
        assertTrue(Objects.nonNull(response));
    }

    @Test
    public void getWeatherReportByGeoCoordinatesTest_Error() {
        Map<String, String> location = new HashMap<>();
        location.put(LATITUDE, "1.58");
        WeatherResponse response = service.getWeatherReportByGeoCoordinates(location);
        assertTrue(Objects.nonNull(response) && response.getMessage().equals("Nothing to geocode"));
    }

}
