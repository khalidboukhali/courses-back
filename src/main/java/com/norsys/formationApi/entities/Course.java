package com.norsys.formationApi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "course_adherent",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "adherent_id")
    )
    private List<Adherent> adherents;
}

