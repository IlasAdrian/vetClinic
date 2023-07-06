package com.sda.vetClinic.controller;

import com.sda.vetClinic.dto.AppointmentDto;
import com.sda.vetClinic.dto.PetDto;
import com.sda.vetClinic.service.AppointmentService;
import com.sda.vetClinic.service.PetService;
import com.sda.vetClinic.validator.PetValidator;
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
public class PetController {
    @Autowired
    PetService petService;
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    PetValidator petValidator;
    @GetMapping("/addPet")
    public String addPetGet(Model model) {
        model.addAttribute("petDto", new PetDto());
        return "addPet";
    }

    @PostMapping("/addPet")
    public String addPetPost(@ModelAttribute(name = "petDto") @Valid PetDto petDto, BindingResult bindingResult, Authentication authentication) {
        petDto.setOwnerEmail(authentication.getName());
        petValidator.validate(petDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "addPet";
        }
        petService.addPet(petDto);
        return "redirect:/addPet";
    }
    @GetMapping("/pets")
    public String viewPetGet(Model model, Authentication authentication) {
        List<PetDto> dtoPets = petService.getPetDtoListByOwnerEmail(authentication.getName());
        model.addAttribute("dtoPets", dtoPets);
        return "pets";
    }
    @GetMapping("/viewPet/{petId}")
    public String viewPetGet(@PathVariable(value = "petId") String petId, Model model) {
        PetDto petDto = petService.getPetDtoById(petId);
        model.addAttribute("petDto", petDto);

        model.addAttribute("petId", petId);

        List<AppointmentDto> dtoAppointments = appointmentService.getAppointmentDtoListByPetId(petId);
        model.addAttribute("dtoAppointments", dtoAppointments);
        return "viewPet";
    }

}
