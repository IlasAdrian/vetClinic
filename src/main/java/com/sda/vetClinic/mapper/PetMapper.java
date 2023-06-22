package com.sda.vetClinic.mapper;

import com.sda.vetClinic.dto.PetDto;
import com.sda.vetClinic.entity.Pet;
import com.sda.vetClinic.enums.Gender;
import com.sda.vetClinic.enums.Specie;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PetMapper {
    public Pet map(PetDto petDto) {
        return Pet.builder()
                .name(petDto.getName())
                .specie(Specie.valueOf(petDto.getSpecie()))
                .breed(petDto.getBreed())
                .gender(Gender.valueOf(petDto.getGender()))
                .dateOfBirth(LocalDate.parse(petDto.getDateOfBirth()))
                .build();
    }
}
