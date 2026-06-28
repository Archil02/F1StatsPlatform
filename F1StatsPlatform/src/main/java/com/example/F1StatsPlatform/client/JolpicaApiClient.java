package com.example.F1StatsPlatform.client;

import com.example.F1StatsPlatform.client.dto.JolpicaDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Collections;
import java.util.List;


@Component
public class JolpicaApiClient {

    private final RestClient restClient;

    public JolpicaApiClient(@Qualifier("jolpicaRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public List<JolpicaDto.Race> fetchSchedule(int season) {
        try {
            JolpicaDto.RootResponse response = restClient.get()
                    .uri("/{season}/races.json?limit=100", season)
                    .retrieve()
                    .body(JolpicaDto.RootResponse.class);

            if (response == null || response.getMrData() == null
                    || response.getMrData().getRaceTable() == null) {
                return Collections.emptyList();
            }

            List<JolpicaDto.Race> races = response.getMrData().getRaceTable().getRaces();
            return races;

        } catch (RestClientException ex) {
            return Collections.emptyList();
        }
    }


    public List<JolpicaDto.RaceResult> fetchRaceResults(int season, int round) {
        try {
            JolpicaDto.RootResponse response = restClient.get()
                    .uri("/{season}/{round}/results.json", season, round)
                    .retrieve()
                    .body(JolpicaDto.RootResponse.class);

            if (response == null || response.getMrData() == null
                    || response.getMrData().getRaceTable() == null) {
                return Collections.emptyList();
            }

            List<JolpicaDto.Race> races = response.getMrData().getRaceTable().getRaces();
            if (races.isEmpty()) return Collections.emptyList();

            List<JolpicaDto.RaceResult> results = races.get(0).getResults();
            return results != null ? results : Collections.emptyList();

        } catch (RestClientException ex) {
            return Collections.emptyList();
        }
    }

    public List<JolpicaDto.DriverStanding> fetchDriverStandings(int season) {
        try {
            JolpicaDto.RootResponse response = restClient.get()
                    .uri("/{season}/driverStandings.json", season)
                    .retrieve()
                    .body(JolpicaDto.RootResponse.class);

            if (response == null || response.getMrData() == null
                    || response.getMrData().getStandingsTable() == null) {
                return Collections.emptyList();
            }

            List<JolpicaDto.StandingsList> standingsLists =
                    response.getMrData().getStandingsTable().getStandingsLists();
            if (standingsLists == null || standingsLists.isEmpty()) return Collections.emptyList();

            List<JolpicaDto.DriverStanding> standings =
                    standingsLists.get(0).getDriverStandings();
            return standings != null ? standings : Collections.emptyList();

        } catch (RestClientException ex) {
            return Collections.emptyList();
        }
    }
}