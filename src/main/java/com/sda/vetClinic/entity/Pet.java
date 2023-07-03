package com.sda.vetClinic.entity;

import com.sda.vetClinic.enums.Gender;
import com.sda.vetClinic.enums.Specie;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Specie specie;
    private String breed;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private LocalDate dateOfBirth;
    @ManyToOne
    @JoinColumn
    private User owner;
    @OneToMany(mappedBy = "pet")
    private List<Appointment> appointments;


}
