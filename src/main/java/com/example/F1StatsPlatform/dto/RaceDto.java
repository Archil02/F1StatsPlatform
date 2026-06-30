package com.example.F1StatsPlatform.dto;

import com.example.F1StatsPlatform.entity.RaceStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class RaceDto {
    private Long id;
    private Integer round;
    private String name;
    private String circuitName;
    private String country;
    private LocalDate raceDate;
    private OffsetDateTime sessionStartUtc;
    private RaceStatus status;
}