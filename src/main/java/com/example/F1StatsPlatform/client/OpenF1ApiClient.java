package com.example.F1StatsPlatform.client;

import com.example.F1StatsPlatform.client.dto.OpenF1SessionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Collections;
import java.util.List;



@Component
public class OpenF1ApiClient {

    private final RestClient restClient;

    public OpenF1ApiClient(@Qualifier("openF1RestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public List<OpenF1SessionDto> fetchRaceSessions(int year) {
        try {
            List<OpenF1SessionDto> sessions = restClient.get()
                    .uri("/sessions?session_name=Race&year={year}", year)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<OpenF1SessionDto>>() {});

            if (sessions == null) {
                return Collections.emptyList();
            }

            return sessions;

        } catch (RestClientException ex) {
            return Collections.emptyList();
        }
    }
}