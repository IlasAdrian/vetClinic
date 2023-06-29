package com.sda.vetClinic.controller;

import com.sda.vetClinic.dto.AppointmentDto;
import com.sda.vetClinic.dto.PetDto;
import com.sda.vetClinic.dto.UserDto;
//import com.sda.vetClinic.service.LoginService;
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
    public String homeGet(Model model){
        return "home";
    }


    @GetMapping("/registration")
    public String registrationGet(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute(name = "userDto")  @Valid UserDto userDto, BindingResult bindingResult) {
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
    public String addPetPost(@ModelAttribute(name = "petDto") @Valid PetDto petDto, BindingResult bindingResult) {
        petValidator.validate(petDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "addPet";
        }
        petService.addPet(petDto);
        return "redirect:/addPet";
    }
    @GetMapping("/addAppointment")
    public String addAppointmentGet(Model model){
        model.addAttribute("appointmentDto", new AppointmentDto());
        return "addAppointment";
    }
    @PostMapping("/addAppointment")
    public String addAppointmentPost(@ModelAttribute(name = "appointmentDto") @Valid AppointmentDto appointmentDto, BindingResult bindingResult){
        appointmentValidator.validate(appointmentDto, bindingResult);
        if(bindingResult.hasErrors()){
            return"addAppointment";
        }
        appointmentService.addAppointment(appointmentDto);
        return "redirect:/addAppointment";
    }


    @GetMapping("/viewPet")
    public String viewPetGet(Model model, Authentication authentication){
        List<PetDto> dtoPets = petService.getPetDtoListByOwnerEmail(authentication.getName());
        model.addAttribute("dtoPets",dtoPets);
        System.out.println(dtoPets);
        return "viewPet";
    }



    @GetMapping("/viewAppointments")
    public String viewAppointmentGet(Model model, Authentication  authentication){
        List<AppointmentDto> dtoAppointments = appointmentService.getAppointmentDtoListByOwnerEmail(authentication.getName());
        model.addAttribute("dtoAppointments",dtoAppointments);
        System.out.println(dtoAppointments);
        return "viewAppointments";
    }
}
