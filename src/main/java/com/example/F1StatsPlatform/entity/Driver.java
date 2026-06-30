package com.example.F1StatsPlatform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_ref", nullable = false, unique = true, length = 100)
    private String externalRef;

    @Column(length = 10)
    private String code;

    private Integer number;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(length = 100)
    private String nationality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "headshot_url", length = 500)
    private String headshotUrl;


    @Builder.Default
    @Column(name = "season_points", nullable = false)
    private Integer seasonPoints = 0;

    @Builder.Default
    @Column(name = "wins", nullable = false)
    private Integer wins = 0;

    @Builder.Default
    @Column(name = "podiums", nullable = false)
    private Integer podiums = 0;

    @Builder.Default
    @Column(name = "races_entered", nullable = false)
    private Integer racesEntered = 0;
}