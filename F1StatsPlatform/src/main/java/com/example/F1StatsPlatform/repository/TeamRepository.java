package com.example.F1StatsPlatform.repository;

import com.example.F1StatsPlatform.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByExternalRef(String externalRef);
}