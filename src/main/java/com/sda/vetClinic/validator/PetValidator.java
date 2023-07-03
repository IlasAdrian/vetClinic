package com.sda.vetClinic.validator;

import com.sda.vetClinic.dto.PetDto;
import com.sda.vetClinic.repository.PetRepository;
import com.sda.vetClinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class PetValidator {
    @Autowired
    PetRepository petRepository;
    @Autowired
    UserRepository userRepository;

    public void validate(PetDto petDto, BindingResult bindingResult){
    }
}
