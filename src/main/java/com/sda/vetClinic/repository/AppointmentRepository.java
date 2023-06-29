package com.sda.vetClinic.repository;

import com.sda.vetClinic.entity.Appointment;
import com.sda.vetClinic.entity.Pet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    List<Appointment> findByPetOwnerEmail(String ownerEmail);
}
