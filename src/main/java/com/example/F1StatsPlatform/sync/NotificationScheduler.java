package com.example.F1StatsPlatform.sync;

import com.example.F1StatsPlatform.entity.Race;
import com.example.F1StatsPlatform.entity.RaceStatus;
import com.example.F1StatsPlatform.repository.RaceRepository;
import com.example.F1StatsPlatform.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final RaceRepository raceRepository;
    private final EmailService emailService;

    @Scheduled(fixedDelay = 60_000)
    @Transactional
    public void checkAndSendNotifications() {
        OffsetDateTime now = OffsetDateTime.now();


        List<Race> startedRaces = raceRepository
                .findAllBySessionStartUtcLessThanEqualAndStartNotificationSentFalse(now);

        for (Race race : startedRaces) {
            emailService.sendRaceStartedEmails(race);
            race.setStartNotificationSent(true);
            race.setStatus(RaceStatus.LIVE);
            raceRepository.save(race);
        }


        List<Race> finishedRaces = raceRepository
                .findAllBySessionEndUtcLessThanEqualAndEndNotificationSentFalse(now);

        for (Race race : finishedRaces) {
            emailService.sendRaceFinishedEmails(race);
            race.setEndNotificationSent(true);
            race.setStatus(RaceStatus.COMPLETED);
            raceRepository.save(race);
        }
    }
}