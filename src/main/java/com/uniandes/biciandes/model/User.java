package com.uniandes.biciandes.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    private String email;
    private String name;
    private String lastName;
    private String documentType;
    private String document;
    private LocalDate birthday;
    private Long weight;
    private Long height;
    private String occupation;
    private String urlPicture;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Bicycle> bicycles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Goal> goals;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Trip> trips;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Photo> photos;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Video> videos;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Group> myOwnGroups;

    @ManyToMany(mappedBy = "members")
    private Set<Group> memberGroup;

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", documentType='" + documentType + '\'' +
                ", document='" + document + '\'' +
                ", birthday=" + birthday +
                ", weight=" + weight +
                ", height=" + height +
                ", occupation='" + occupation + '\'' +
                ", urlPicture='" + urlPicture + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
