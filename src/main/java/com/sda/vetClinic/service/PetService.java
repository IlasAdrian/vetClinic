package com.sda.vetClinic.service;

import com.sda.vetClinic.dto.PetDto;
import com.sda.vetClinic.entity.Pet;
import com.sda.vetClinic.mapper.PetMapper;
import com.sda.vetClinic.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    @Autowired
    private PetMapper petMapper;
    @Autowired
    private PetRepository petRepository;

    public void addPet(PetDto petDto) {
        Pet pet = petMapper.map(petDto);
        petRepository.save(pet);
    }
}
