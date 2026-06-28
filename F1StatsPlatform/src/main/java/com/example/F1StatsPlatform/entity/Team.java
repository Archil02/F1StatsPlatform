package com.example.F1StatsPlatform.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_ref", nullable = false, unique = true, length = 100)
    private String externalRef;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 100)
    private String nationality;
}