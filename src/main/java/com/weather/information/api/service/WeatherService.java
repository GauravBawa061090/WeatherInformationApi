package com.weather.information.api.service;

import com.weather.information.api.model.WeatherResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Map;

import static com.weather.information.api.constants.WeatherConstantsIfc.*;

@Log4j2
@Service
public class WeatherService {

    @Value("${weather.info.url}")
    private String url;

    @Value("${weather.info.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CacheManager cacheManager;

    @Cacheable(cacheNames = CACHE_LOCATION, condition = "#location.get('region') != null", key = "#location.get('region')")
    public WeatherResponse getWeatherReportByLocation(Map<String, String> location) {
        if(ObjectUtils.isEmpty(location))
        {
            WeatherResponse response = new WeatherResponse();
            response.setMessage(INVALID_ATTR + CITY);
            return ResponseEntity.badRequest().body(response).getBody();
        }
        WeatherResponse response = queryByLocation(url, apiKey, location).getBody();
        log.info("Sending response of weather report by city "+response);
        return response;
    }

    @Cacheable(cacheNames = CACHE_GEOLOCATION, condition = "#location.get('lat') != null && #location.get('lon') != null", key = "#location.get('lat').concat('-').concat(#location.get('lon'))")
    public WeatherResponse getWeatherReportByGeoCoordinates(Map<String, String> location) {
        if(ObjectUtils.isEmpty(location))
        {
            WeatherResponse response = new WeatherResponse();
            response.setMessage(INVALID_ATTR + LONGITUDE + " AND "+LATITUDE);
            return ResponseEntity.badRequest().body(response).getBody();
        }
        WeatherResponse response = queryByGeoCoordinates(url, apiKey, location).getBody();
        log.info("Sending response of weather report by geo coordinates "+response);
        return response;
    }

    public ResponseEntity<WeatherResponse> queryByLocation(String url, String apiKey, Map<String, String> requestParam) {
        log.info("Sending request to get weather report by city");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(LOCATION_PARAM, requestParam.get(CITY))
                .queryParam(APPID, apiKey);
        return restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null, WeatherResponse.class);
    }

    public ResponseEntity<WeatherResponse> queryByGeoCoordinates(String url, String apiKey, Map<String, String> requestParam) {
        log.info("Sending request to get weather report by geo coordinates");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(LATITUDE, requestParam.get(LATITUDE))
                .queryParam(LONGITUDE, requestParam.get(LONGITUDE))
                .queryParam(APPID, apiKey);
        return restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null, WeatherResponse.class);
    }


    @Scheduled(fixedRateString = "${weather.info.evict.time}")
    public void evictWeatherReportByLocation() {
        log.info("Deleting from " + CACHE_LOCATION + " cache at " + LocalDateTime.now());
        cacheManager.getCache(CACHE_LOCATION).clear();
    }

    @Scheduled(fixedRateString = "${weather.info.evict.time}")
    public void evictByGeoCoordinates() {
        log.info("Deleting from " + CACHE_GEOLOCATION + " cache at " + LocalDateTime.now());
        cacheManager.getCache(CACHE_GEOLOCATION).clear();
    }

}
