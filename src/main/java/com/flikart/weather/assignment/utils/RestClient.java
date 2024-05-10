package com.flikart.weather.assignment.utils;

import com.flikart.weather.assignment.enums.FlipkartErrorResponse;
import com.flikart.weather.assignment.exceptions.GenericException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
public class RestClient {
    RestTemplate restTemplate = new RestTemplate();

    public String doGetRequest(String uri, Map<String, String> params, HttpHeaders headers) {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(uri);
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach(uriComponentsBuilder::queryParam);
        }
        try {
            ResponseEntity<String> result = restTemplate.exchange(uriComponentsBuilder.build().toUri(), HttpMethod.GET,
                    entity, String.class);
            if (result.getStatusCode().is2xxSuccessful() && result.hasBody()) {
                return result.getBody();
            } else
                throw new GenericException(FlipkartErrorResponse.FKE006, Collections.singletonList(result.getBody()));
        } catch (Exception e) {
            log.error("Exception occurred while calling third party api with message :: {}", e.getMessage());
            throw new GenericException(FlipkartErrorResponse.FKE006, Collections.singletonList(e.getMessage()));
        }
    }
}
