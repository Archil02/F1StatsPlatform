package com.example.F1StatsPlatform.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DriverDto {
    private Long id;
    private String code;
    private Integer number;
    private String firstName;
    private String lastName;
    private String nationality;
    private String teamName;
    private Integer seasonPoints;
    private Integer wins;
    private Integer podiums;
    private Integer racesEntered;
}