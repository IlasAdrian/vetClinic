package com.sda.vetClinic.controller;

import com.sda.vetClinic.dto.AppointmentDto;
import com.sda.vetClinic.dto.PetDto;
import com.sda.vetClinic.service.AppointmentService;
import com.sda.vetClinic.service.PetService;
import com.sda.vetClinic.validator.AppointmentValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AppointmentController {
    @Autowired
    PetService petService;
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    AppointmentValidator appointmentValidator;
    @GetMapping("/addAppointment")
    public String addAppointmentGet(Model model, Authentication authentication) {
        model.addAttribute("appointmentDto", new AppointmentDto());

        List<String> petNames = petService.getPetNameListByOwnerEmail(authentication.getName());
        model.addAttribute("petNameList", petNames);

        return "addAppointment";
    }
    @PostMapping("/addAppointment")
    public String addAppointmentPost(@ModelAttribute(name = "appointmentDto") @Valid AppointmentDto appointmentDto,
                                     BindingResult bindingResult, Authentication authentication) {
        appointmentDto.setPetId(petService.GetPetIdByName(appointmentDto.getPetId()));
        appointmentValidator.validate(appointmentDto, bindingResult, authentication.getName());
        if (bindingResult.hasErrors()) {
            return "addAppointment";
        }
        appointmentService.addAppointment(appointmentDto);
        return "redirect:/addAppointment";
    }


    @GetMapping("/appointments")
    public String viewAppointmentGet(Model model, Authentication authentication) {
        List<AppointmentDto> dtoAppointments = appointmentService.getAppointmentDtoListByOwnerEmail(authentication.getName());
        model.addAttribute("dtoAppointments", dtoAppointments);
        return "appointments";
    }

    @GetMapping("/appointmentList")
    public String editAppointmentGet(Model model) {
        List<AppointmentDto> dtoAppointments = appointmentService.getAllAppointments();
        model.addAttribute("dtoAppointments", dtoAppointments);

        model.addAttribute("editDtoAppointment", new AppointmentDto());
        return "appointmentList";
    }

    @PostMapping("/appointmentList/{appointmentId}")
    public String editAppointmentPost(@PathVariable(value = "appointmentId") String appointmentId,
                                      AppointmentDto editDtoAppointment, Authentication authentication) {
        appointmentService.editAppointment(appointmentId, editDtoAppointment.getDate(), authentication.getName());
        return "redirect:/appointmentList";
    }

    @GetMapping("/viewAppointment/{appointmentId}")
    public String viewAppointmentGet(@PathVariable(value = "appointmentId") String appointmentId,
                                     Model model, Authentication authentication) {
        AppointmentDto appointmentDto = appointmentService.getAppointmentDtoById(appointmentId);
        model.addAttribute("appointmentDto", appointmentDto);

        PetDto petDto = petService.getPetDtoById(appointmentDto.getPetId());
        model.addAttribute("petDto", petDto);

        model.addAttribute("isOwner", appointmentService.isOwner(authentication.getName(), appointmentDto.getPetId()));

        model.addAttribute("editDtoAppointment", new AppointmentDto());
        return "viewAppointment";
    }

    @PostMapping("/viewAppointment/{appointmentId}")
    public String editAppointmentPost(@PathVariable(value = "appointmentId") String appointmentId, String description,
                                      String status, String review, String date, Model model) {
        AppointmentDto editDtoAppointment = appointmentService.getAppointmentDtoById(appointmentId);
        appointmentService.editAppointment(appointmentId, description, status, date, review);
        model.addAttribute("editDtoAppointment", editDtoAppointment);
        return "redirect:/viewAppointment/{appointmentId}";
    }
    @GetMapping("/viewAppointmentList")
    public String viewAppointmentListGet(Model model, Authentication authentication) {
        List<AppointmentDto> dtoAppointments = appointmentService.getAppointmentDtoListByVetEmail(authentication.getName());
        model.addAttribute("dtoAppointments", dtoAppointments);
        return "viewAppointmentList";
    }
}
