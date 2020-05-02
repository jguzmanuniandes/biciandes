package com.uniandes.biciandes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String description;
    private LocalDate date;
    private String resolution;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
