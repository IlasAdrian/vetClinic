package com.sda.vetClinic.service;

import com.sda.vetClinic.dto.AppointmentDto;
import com.sda.vetClinic.entity.Appointment;
import com.sda.vetClinic.entity.Pet;
import com.sda.vetClinic.enums.Status;
import com.sda.vetClinic.mapper.AppointmentMapper;
import com.sda.vetClinic.repository.AppointmentRepository;
import com.sda.vetClinic.repository.PetRepository;
import com.sda.vetClinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetRepository petRepository;

    public void addAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = appointmentMapper.map(appointmentDto);
        appointmentRepository.save(appointment);
    }


    public List<AppointmentDto> getAppointmentDtoListByOwnerEmail(String ownerEmail) {
        List<Appointment> appointments = appointmentRepository.findByPetOwnerEmail(ownerEmail);
        return appointmentMapper.map(appointments);

    }

    public List<AppointmentDto> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointmentMapper.map(appointments);
    }

    public List<AppointmentDto> getAppointmentDtoListByPetId(String petId) {
        List<Appointment> appointments = appointmentRepository.findByPetId(petId);
        return appointmentMapper.map(appointments);
    }

    public void editAppointment(String appointmentId, String date, String vetEmail) {
        Appointment appointment = appointmentRepository.findById(appointmentId);
        appointment.setDate(LocalDate.parse(date));
        appointment.setVet(userRepository.findUserByEmail(vetEmail));
        appointment.setStatus(Status.SCHEDULED);
        appointmentRepository.save(appointment);
    }

    public AppointmentDto getAppointmentDtoById(String appointmentId) {
        return appointmentMapper.map(appointmentRepository.findById(appointmentId));
    }

    public void editAppointment(String appointmentId, String description, String status, String date, String review) {
        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (description != null) {
            appointment.setDescription(appointment.getDescription() + "\n" + description);
        }
        if (status !=null && !status.equals(String.valueOf(appointment.getStatus()))) {
            appointment.setStatus(Status.valueOf(status));
        }
        if (date !=null && !date.equals("")) {
            appointment.setDate((LocalDate.parse(date)));
        }
        if(review !=null){
            appointment.setReview((review));
        }
        appointmentRepository.save(appointment);

    }

    public List<AppointmentDto> getAppointmentDtoListByVetEmail(String vetEmail) {
        List<Appointment> appointments = appointmentRepository.findByVetEmail(vetEmail);
        return appointmentMapper.map(appointments);
    }
    public  boolean isOwner(String authEmail, String petId){
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if(optionalPet.isEmpty()){
            return false;
        }
        Pet pet = optionalPet.get();
        return authEmail.equals(pet.getOwner().getEmail());
    }
}
