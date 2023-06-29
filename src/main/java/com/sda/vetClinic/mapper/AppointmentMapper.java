package com.sda.vetClinic.mapper;

import com.sda.vetClinic.dto.AppointmentDto;
import com.sda.vetClinic.dto.PetDto;
import com.sda.vetClinic.entity.Appointment;
import com.sda.vetClinic.entity.Pet;
import com.sda.vetClinic.entity.User;
import com.sda.vetClinic.enums.Status;
import com.sda.vetClinic.enums.Type;
import com.sda.vetClinic.repository.PetRepository;
import com.sda.vetClinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class AppointmentMapper {
    @Autowired
    PetRepository petRepository;
    @Autowired
    UserRepository userRepository;

    public Appointment map(AppointmentDto appointmentDto) {
        Optional<Pet> optionalPet = petRepository.findById(appointmentDto.getPetId());
        if (optionalPet.isEmpty()) {
            throw new RuntimeException("No pet found");
        }
//        Optional<User> optionalUser = userRepository.findById(appointmentDto.getVetId());
//        if(optionalUser.isEmpty()){
//            throw new RuntimeException("No vet found");
//        }
        return Appointment.builder()
                .pet(optionalPet.get())
                .type(Type.valueOf(appointmentDto.getType()))
                .description((appointmentDto.getDescription()))
                .status(Status.FREE)
                .build();
    }

    public AppointmentDto map(Appointment appointment) {
        return AppointmentDto.builder()
                .petId(String.valueOf(appointment.getPet().getId()))
                .type(appointment.getType().toString())
                .description(appointment.getDescription())
                .status(appointment.getStatus().toString())
                .build();
    }

    public List<AppointmentDto> map(List<Appointment> appointments) {
        return appointments.stream().map(this ::map).toList();
    }
}
