package com.sda.vetClinic.controller;

import com.sda.vetClinic.dto.UserDto;
import com.sda.vetClinic.service.UserService;
import com.sda.vetClinic.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserValidator userValidator;
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
}
