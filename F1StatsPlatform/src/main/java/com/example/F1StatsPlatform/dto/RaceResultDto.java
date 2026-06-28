package com.example.F1StatsPlatform.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RaceResultDto {
    private Integer finishPosition;
    private Integer gridPosition;
    private String driverCode;
    private String driverFirstName;
    private String driverLastName;
    private String teamName;
    private Integer points;
    private Integer laps;
    private String status;
}