package com.example.F1StatsPlatform.repository;

import com.example.F1StatsPlatform.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByExternalRef(String externalRef);


    @Query("SELECT d FROM Driver d LEFT JOIN FETCH d.team ORDER BY d.seasonPoints DESC")
    List<Driver> findAllByOrderBySeasonPointsDesc();
}