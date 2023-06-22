package com.sda.vetClinic.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PetDto {
    private String name;
    private String specie;
    private String breed;
    private String gender;
    private String dateOfBirth;

}
