package com.sda.vetClinic.controller;

import com.sda.vetClinic.dto.AppointmentDto;
import com.sda.vetClinic.dto.PetDto;
import com.sda.vetClinic.dto.UserDto;
import com.sda.vetClinic.service.AppointmentService;
import com.sda.vetClinic.service.PetService;
import com.sda.vetClinic.service.UserService;
import com.sda.vetClinic.validator.AppointmentValidator;
import com.sda.vetClinic.validator.PetValidator;
import com.sda.vetClinic.validator.UserValidator;
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
public class MvcController {
    @Autowired
    private PetService petService;
    @Autowired
    private UserService userService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private PetValidator petValidator;
    @Autowired
    private AppointmentValidator appointmentValidator;

    @GetMapping("/home")
    public String homeGet(Model model) {
        return "home";
    }


    @GetMapping("/registration")
    public String registrationGet(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute(name = "userDto") @Valid UserDto userDto, BindingResult bindingResult) {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.addUser(userDto);
        return "redirect:/registration";
    }

    @GetMapping("/login")
    public String loginGet(Model model) {
        return "login";
    }

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

    @GetMapping("/addAppointment")
    public String addAppointmentGet(Model model, Authentication authentication) {
        model.addAttribute("appointmentDto", new AppointmentDto());

        List<String> petNames = petService.getPetNameListByOwnerEmail(authentication.getName());
        model.addAttribute("petNameList", petNames);

        return "addAppointment";
    }

    @PostMapping("/addAppointment")
    public String addAppointmentPost(@ModelAttribute(name = "appointmentDto") @Valid AppointmentDto appointmentDto, BindingResult bindingResult) {
        appointmentDto.setPetId(petService.GetPetIdByName(appointmentDto.getPetId()));
        appointmentValidator.validate(appointmentDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "addAppointment";
        }
        appointmentService.addAppointment(appointmentDto);
        return "redirect:/addAppointment";
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
    public String viewAppointmentGet(@PathVariable(value = "appointmentId") String appointmentId, Model model) {
        AppointmentDto appointmentDto = appointmentService.getAppointmentDtoById(appointmentId);
        model.addAttribute("appointmentDto", appointmentDto);

        PetDto petDto = petService.getPetDtoById(appointmentDto.getPetId());
        model.addAttribute("petDto", petDto);

        model.addAttribute("editDtoAppointment", new AppointmentDto());
        return "viewAppointment";
    }

    @PostMapping("/viewAppointment/{appointmentId}")
    public String editAppointmentPost(@PathVariable(value = "appointmentId") String appointmentId, String description,
                                      String status, String date, Model model) {
        AppointmentDto editDtoAppointment = appointmentService.getAppointmentDtoById(appointmentId);
        appointmentService.editAppointment(appointmentId, description, status, date);
        model.addAttribute("editDtoAppointment", editDtoAppointment);
        return "redirect:/viewAppointment/{appointmentId}";
    }
}
