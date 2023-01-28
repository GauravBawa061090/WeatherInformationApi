package com.weather.information.api.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Log4j2
public class ErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return new DefaultResponseErrorHandler().hasError(clientHttpResponse);
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        log.error("Error while fetching the weather information : " + clientHttpResponse.getStatusCode());
        ResponseEntity.status(clientHttpResponse.getStatusCode()).body(clientHttpResponse.getBody());
    }
}
