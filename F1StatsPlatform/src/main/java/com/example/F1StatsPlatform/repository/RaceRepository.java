package com.example.F1StatsPlatform.repository;

import com.example.F1StatsPlatform.entity.Race;
import com.example.F1StatsPlatform.entity.RaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface RaceRepository extends JpaRepository<Race, Long> {

    Optional<Race> findByExternalRef(String externalRef);

    List<Race> findAllBySeasonOrderByRoundAsc(Integer season);

    List<Race> findAllByStatusOrderByRaceDateAsc(RaceStatus status);

    List<Race> findAllBySessionStartUtcLessThanEqualAndStartNotificationSentFalse(OffsetDateTime now);
    List<Race> findAllBySessionEndUtcLessThanEqualAndEndNotificationSentFalse(OffsetDateTime now);
}
