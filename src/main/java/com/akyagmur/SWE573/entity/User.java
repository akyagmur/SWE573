package com.akyagmur.SWE573.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    private LocalDate birthDate;
    private String password;
    private LocalDateTime createdAt;

    /* Fill createdAt */
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
