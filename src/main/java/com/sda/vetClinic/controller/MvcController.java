package com.sda.vetClinic.controller;

import com.sda.vetClinic.dto.PetDto;
import com.sda.vetClinic.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MvcController {
    @Autowired
    private PetService petService;

    @GetMapping("/addPet")
    public String addPetGet(Model model) {
        model.addAttribute("petDto", new PetDto());
        return "addPet";
    }

    @PostMapping("/addPet")
    public String addPetPost(@ModelAttribute(name = "petDto") PetDto petDto) {
        petService.addPet(petDto);
        return "redirect:/addPet";
    }
}
