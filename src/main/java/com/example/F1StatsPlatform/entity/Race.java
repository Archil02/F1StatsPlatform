package com.example.F1StatsPlatform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "races")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_ref", nullable = false, unique = true, length = 100)
    private String externalRef;

    @Column(nullable = false)
    private Integer season;

    @Column(nullable = false)
    private Integer round;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "circuit_name", length = 150)
    private String circuitName;

    @Column(length = 100)
    private String country;

    @Column(name = "race_date", nullable = false)
    private LocalDate raceDate;

    @Column(name = "session_start_utc")
    private OffsetDateTime sessionStartUtc;

    @Column(name = "session_end_utc")
    private OffsetDateTime sessionEndUtc;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RaceStatus status = RaceStatus.UPCOMING;

    @Builder.Default
    @Column(name = "start_notification_sent", nullable = false)
    private Boolean startNotificationSent = false;

    @Builder.Default
    @Column(name = "end_notification_sent", nullable = false)
    private Boolean endNotificationSent = false;
}