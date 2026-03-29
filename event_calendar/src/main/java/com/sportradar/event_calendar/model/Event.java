package com.sportradar.event_calendar.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateVenue;
    private LocalTime timeVenueUTC;
    private String status;
    private Integer homeGoals;
    private Integer awayGoals;

    @ManyToOne
    @JoinColumn(name = "_sport_id")
    private Sport sport;

    @ManyToOne
    @JoinColumn(name = "_home_team_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "_away_team_id")
    private Team awayTeam;
}