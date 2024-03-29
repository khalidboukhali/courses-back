package com.norsys.formationApi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Adherent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    @ManyToMany
    private List<Course> courses;

    public Adherent() {
        this.courses = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Adherent{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName + "', email='" + email + "'}";
    }
}

