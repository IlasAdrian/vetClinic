package com.sda.vetClinic.repository;

import com.sda.vetClinic.entity.Pet;
import com.sda.vetClinic.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PetRepository extends CrudRepository<Pet, Long> {
    Optional<Pet> findByOwnerEmail(String email);
    Optional<Pet> findById(String id);
}
