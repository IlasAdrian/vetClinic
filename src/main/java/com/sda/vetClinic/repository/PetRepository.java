package com.sda.vetClinic.repository;

import com.sda.vetClinic.entity.Pet;
import com.sda.vetClinic.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends CrudRepository<Pet, Long> {
    List<Pet> findByOwnerEmail(String email);
    Optional<Pet> findById(String id);
    Pet findPetById(String id);
    Pet findByName(String name);

}
