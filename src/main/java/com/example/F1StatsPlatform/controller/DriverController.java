package com.example.F1StatsPlatform.controller;

import com.example.F1StatsPlatform.dto.DriverDto;
import com.example.F1StatsPlatform.service.F1DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final F1DataService f1DataService;

    @GetMapping
    public ResponseEntity<List<DriverDto>> getDriverStandings() {
        return ResponseEntity.ok(f1DataService.getDriverStandings());
    }
}