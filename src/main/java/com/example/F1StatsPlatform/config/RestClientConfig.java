package com.example.F1StatsPlatform.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean("jolpicaRestClient")
    public RestClient jolpicaRestClient(
            @Value("${app.external-api.jolpica-base-url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Bean("openF1RestClient")
    public RestClient openF1RestClient(
            @Value("${app.external-api.openf1-base-url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}