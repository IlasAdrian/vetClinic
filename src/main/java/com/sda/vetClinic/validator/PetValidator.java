package com.sda.vetClinic.validator;

import com.sda.vetClinic.dto.PetDto;
import com.sda.vetClinic.dto.UserDto;
import com.sda.vetClinic.entity.Pet;
import com.sda.vetClinic.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;

@Component
public class PetValidator {
    @Autowired
    PetRepository petRepository;

    public void validate(PetDto petDto, BindingResult bindingResult){
        validateEmail(petDto, bindingResult);
    }
    private void validateEmail(PetDto petDto, BindingResult bindingResult) {
        if (validateEmailNotEmpty(petDto, bindingResult)) return;
        validateEmailIsPresent(petDto, bindingResult);
    }

    private boolean validateEmailNotEmpty(PetDto petDto, BindingResult bindingResult) {
        if (petDto.getOwnerEmail().isEmpty()) {
            FieldError fieldError = new FieldError("petDto", "ownerEmail",
                    "Email should not be empty!");
            bindingResult.addError(fieldError);
            return true;
        }
        return false;
    }

    private void validateEmailIsPresent(PetDto petDto, BindingResult bindingResult) {
        Optional<Pet> optionalPet = petRepository.findByOwnerEmail(petDto.getOwnerEmail());
        if(!optionalPet.isPresent()){
            FieldError fieldError = new FieldError("petDto","ownerEmail",
                    "Incorrect owner's email!");
            bindingResult.addError(fieldError);
        }
    }
}