package com.sda.vetClinic.entity;

import com.sda.vetClinic.enums.Role;
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
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "vet")
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "owner")
    private List<Pet> pets;
}
