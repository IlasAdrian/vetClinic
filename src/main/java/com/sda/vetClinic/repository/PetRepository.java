package com.sda.vetClinic.repository;

import com.sda.vetClinic.entity.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
