package com.uniandes.biciandes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Boolean isPrivate;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User owner;

    @ManyToMany
    @JoinTable
    private Set<User> members;
}
