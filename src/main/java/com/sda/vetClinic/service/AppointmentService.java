package com.sda.vetClinic.service;

import com.sda.vetClinic.dto.AppointmentDto;
import com.sda.vetClinic.dto.PetDto;
import com.sda.vetClinic.entity.Appointment;
import com.sda.vetClinic.entity.Pet;
import com.sda.vetClinic.entity.User;
import com.sda.vetClinic.mapper.AppointmentMapper;
import com.sda.vetClinic.repository.AppointmentRepository;
import com.sda.vetClinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private AppointmentRepository appointmentRepository;

    public void addAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = appointmentMapper.map(appointmentDto);
        appointmentRepository.save(appointment);
    }


    public List<AppointmentDto> getAppointmentDtoListByOwnerEmail(String ownerEmail) {
        List<Appointment> appointments = appointmentRepository.findByPetOwnerEmail(ownerEmail);
        return appointmentMapper.map(appointments);

    }
}
