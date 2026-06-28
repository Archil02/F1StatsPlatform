package com.example.F1StatsPlatform.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenF1SessionDto {

    @JsonProperty("session_key")
    private Integer sessionKey;

    @JsonProperty("session_name")
    private String sessionName;

    @JsonProperty("session_type")
    private String sessionType;

    @JsonProperty("meeting_name")
    private String meetingName;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("date_start")
    private String dateStart;

    @JsonProperty("date_end")
    private String dateEnd;
}