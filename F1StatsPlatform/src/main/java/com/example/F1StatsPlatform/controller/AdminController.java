package com.example.F1StatsPlatform.controller;

import com.example.F1StatsPlatform.sync.DataSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DataSyncService dataSyncService;

    @PostMapping("/sync")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> triggerSync(
            @RequestParam(defaultValue = "0") int season) {

        int targetSeason = season > 0 ? season : Year.now().getValue();
        dataSyncService.syncAll(targetSeason);
        return ResponseEntity.ok(Map.of(
                "status", "ok",
                "message", "Sync დასრულდა (season=" + targetSeason + ")"
        ));
    }
}