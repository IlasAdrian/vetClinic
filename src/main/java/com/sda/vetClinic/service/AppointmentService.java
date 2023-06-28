package com.sda.vetClinic.service;

import com.sda.vetClinic.dto.AppointmentDto;
import com.sda.vetClinic.entity.Appointment;
import com.sda.vetClinic.mapper.AppointmentMapper;
import com.sda.vetClinic.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
