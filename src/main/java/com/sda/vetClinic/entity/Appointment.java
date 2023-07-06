package com.sda.vetClinic.entity;

import com.sda.vetClinic.enums.Status;
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
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn
    private User vet;
    private LocalDate date;
    private String review;
}
