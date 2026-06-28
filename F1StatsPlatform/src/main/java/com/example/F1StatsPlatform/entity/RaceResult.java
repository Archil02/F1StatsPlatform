package com.example.F1StatsPlatform.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
        name = "race_results",
        uniqueConstraints = @UniqueConstraint(columnNames = {"race_id", "driver_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaceResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "finish_position")
    private Integer finishPosition;

    @Column(name = "grid_position")
    private Integer gridPosition;

    @Builder.Default
    @Column(nullable = false)
    private Integer points = 0;

    private Integer laps;

    @Column(length = 50)
    private String status;
}