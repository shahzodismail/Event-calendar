package com.sportradar.event_calendar.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}