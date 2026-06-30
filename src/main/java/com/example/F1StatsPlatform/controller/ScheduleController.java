package com.example.F1StatsPlatform.controller;

import com.example.F1StatsPlatform.dto.RaceDto;
import com.example.F1StatsPlatform.service.F1DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final F1DataService f1DataService;

    @GetMapping
    public ResponseEntity<List<RaceDto>> getSchedule() {
        return ResponseEntity.ok(f1DataService.getSchedule());
    }
}