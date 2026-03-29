package com.sportradar.event_calendar.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String officialName;
    private String abbreviation;
    private String teamCountryCode;
}