package com.example.F1StatsPlatform.repository;

import com.example.F1StatsPlatform.entity.RaceResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RaceResultRepository extends JpaRepository<RaceResult, Long> {

    List<RaceResult> findAllByRaceIdOrderByFinishPositionAsc(Long raceId);

    List<RaceResult> findAllByDriverId(Long driverId);

    Optional<RaceResult> findByRaceIdAndDriverId(Long raceId, Long driverId);
}