package com.example.F1StatsPlatform.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class JolpicaDto {


    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RootResponse {
        @JsonProperty("MRData")
        private MRData mrData;
    }

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MRData {
        @JsonProperty("RaceTable")
        private RaceTable raceTable;

        @JsonProperty("StandingsTable")
        private StandingsTable standingsTable;

        @JsonProperty("ConstructorTable")
        private ConstructorTable constructorTable;
    }


    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RaceTable {
        @JsonProperty("season")
        private String season;

        @JsonProperty("Races")
        private List<Race> races;
    }

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Race {
        @JsonProperty("season")
        private String season;

        @JsonProperty("round")
        private String round;

        @JsonProperty("raceName")
        private String raceName;

        @JsonProperty("Circuit")
        private Circuit circuit;

        @JsonProperty("date")
        private String date;

        @JsonProperty("time")
        private String time;

        @JsonProperty("Results")
        private List<RaceResult> results;
    }

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Circuit {
        @JsonProperty("circuitName")
        private String circuitName;

        @JsonProperty("Location")
        private Location location;
    }

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location {
        @JsonProperty("country")
        private String country;
    }

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RaceResult {
        @JsonProperty("position")
        private String position;

        @JsonProperty("points")
        private String points;

        @JsonProperty("grid")
        private String grid;

        @JsonProperty("laps")
        private String laps;

        @JsonProperty("status")
        private String status;

        @JsonProperty("Driver")
        private Driver driver;

        @JsonProperty("Constructor")
        private Constructor constructor;
    }


    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Driver {
        @JsonProperty("driverId")
        private String driverId;

        @JsonProperty("code")
        private String code;

        @JsonProperty("permanentNumber")
        private String permanentNumber;

        @JsonProperty("givenName")
        private String givenName;

        @JsonProperty("familyName")
        private String familyName;

        @JsonProperty("nationality")
        private String nationality;
    }


    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Constructor {
        @JsonProperty("constructorId")
        private String constructorId;

        @JsonProperty("name")
        private String name;

        @JsonProperty("nationality")
        private String nationality;
    }

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ConstructorTable {
        @JsonProperty("Constructors")
        private List<Constructor> constructors;
    }


    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StandingsTable {
        @JsonProperty("StandingsLists")
        private List<StandingsList> standingsLists;
    }

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StandingsList {
        @JsonProperty("DriverStandings")
        private List<DriverStanding> driverStandings;
    }

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DriverStanding {
        @JsonProperty("position")
        private String position;

        @JsonProperty("points")
        private String points;

        @JsonProperty("wins")
        private String wins;

        @JsonProperty("Driver")
        private Driver driver;

        @JsonProperty("Constructors")
        private List<Constructor> constructors;
    }
}