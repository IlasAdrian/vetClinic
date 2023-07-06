package com.sda.vetClinic.repository;

import com.sda.vetClinic.entity.Appointment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    List<Appointment> findByPetOwnerEmail(String ownerEmail);
    List<Appointment> findAll();

    List<Appointment> findByPetId(String petId);
    Appointment findById(String id);

    List<Appointment> findByVetEmail(String vetEmail);
}
