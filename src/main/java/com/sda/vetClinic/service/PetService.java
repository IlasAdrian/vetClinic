package com.sda.vetClinic.service;

import com.sda.vetClinic.dto.PetDto;
import com.sda.vetClinic.entity.Pet;
import com.sda.vetClinic.mapper.PetMapper;
import com.sda.vetClinic.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<PetDto> getPetDtoListByOwnerEmail(String ownerEmail) {
        List<Pet> pets = petRepository.findByOwnerEmail(ownerEmail);
        return petMapper.map(pets);
    }
    public  List<String> getPetNameListByOwnerEmail(String ownerEmail){
        List<Pet> pets = petRepository.findByOwnerEmail(ownerEmail);
        return pets.stream()
                .map(Pet::getName)
                .toList();
    }

    public String GetPetIdByName(String name) {
        Pet pet = petRepository.findByName(name);
        return String.valueOf(pet.getId());
    }

    public PetDto getPetDtoById(String petId) {
        Pet pet = petRepository.findPetById(petId);
        return petMapper.map(pet);
    }
}
