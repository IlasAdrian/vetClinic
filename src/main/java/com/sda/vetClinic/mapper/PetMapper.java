package com.sda.vetClinic.mapper;

import com.sda.vetClinic.dto.PetDto;
import com.sda.vetClinic.dto.UserDto;
import com.sda.vetClinic.entity.Pet;
import com.sda.vetClinic.entity.User;
import com.sda.vetClinic.enums.Gender;
import com.sda.vetClinic.enums.Specie;
import com.sda.vetClinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class PetMapper {
    @Autowired
    private UserRepository userRepository;
    public Pet map(PetDto petDto) {
        Optional<User> optionalUser = userRepository.findByEmail(petDto.getOwnerEmail());
        if(optionalUser.isEmpty()){
            throw new RuntimeException("No user found");
        }
        return Pet.builder()
                .owner(optionalUser.get())
                .name(petDto.getName())
                .specie(Specie.valueOf(petDto.getSpecie()))
                .breed(petDto.getBreed())
                .gender(Gender.valueOf(petDto.getGender()))
                .dateOfBirth(LocalDate.parse(petDto.getDateOfBirth()))
                .build();
    }
    public PetDto map(Pet pet){
        return PetDto.builder()
                .ownerEmail(pet.getOwner().getEmail())
                .name(pet.getName())
                .specie(String.valueOf(pet.getSpecie()))
                .breed(pet.getBreed())
                .dateOfBirth(pet.getDateOfBirth().toString())
                .build();
    }
}
