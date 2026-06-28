package com.example.F1StatsPlatform.sync;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class SyncScheduler {

    private final DataSyncService dataSyncService;

    @Scheduled(cron = "0 0 4 * * *", zone = "UTC")
    public void dailySync() {
        int currentSeason = Year.now().getValue();
        dataSyncService.syncAll(currentSeason);
    }

    @Scheduled(cron = "0 0 14,17,21 * * SUN", zone = "UTC")
    public void raceDaySync() {
        int currentSeason = Year.now().getValue();
        dataSyncService.syncAll(currentSeason);
    }
}