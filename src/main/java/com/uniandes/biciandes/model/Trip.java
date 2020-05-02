package com.uniandes.biciandes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String minAltitude;
    private String maxAltitude;
    private String minSpeed;
    private String maxSpeed;
    private Long distance;
    private LocalDate date;
    private LocalTime startHour;
    private LocalTime endHour;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
