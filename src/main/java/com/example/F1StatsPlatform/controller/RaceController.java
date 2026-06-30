package com.example.F1StatsPlatform.controller;

import com.example.F1StatsPlatform.dto.RaceResultDto;
import com.example.F1StatsPlatform.service.F1DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/races")
@RequiredArgsConstructor
public class RaceController {

    private final F1DataService f1DataService;

    @GetMapping("/{id}/results")
    public ResponseEntity<List<RaceResultDto>> getResults(@PathVariable Long id) {
        return ResponseEntity.ok(f1DataService.getRaceResults(id));
    }
}