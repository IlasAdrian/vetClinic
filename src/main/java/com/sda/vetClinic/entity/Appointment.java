package com.sda.vetClinic.entity;

import com.sda.vetClinic.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn
    private Pet pet;
    @Enumerated(value = EnumType.STRING)
    private Type type;
    private String description;
    @ManyToOne
    @JoinColumn
    private User vet;
    private LocalDate date;
}
