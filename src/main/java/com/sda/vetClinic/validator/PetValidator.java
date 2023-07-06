package com.sda.vetClinic.validator;

import com.sda.vetClinic.dto.PetDto;
import com.sda.vetClinic.enums.Gender;
import com.sda.vetClinic.enums.Specie;
import com.sda.vetClinic.repository.PetRepository;
import com.sda.vetClinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class PetValidator {
    @Autowired
    PetRepository petRepository;
    @Autowired
    UserRepository userRepository;

    public void validate(PetDto petDto, BindingResult bindingResult) {
        validateName(petDto, bindingResult);
        validateSpecie(petDto, bindingResult);
        validateBreed(petDto, bindingResult);
        validateGender(petDto, bindingResult);
        validateDateOfBirth(petDto, bindingResult);
    }

    private void validateName(PetDto petDto, BindingResult bindingResult) {
        if (validateNameNotEmpty(petDto, bindingResult)) return;
        validateNameFormat(petDto, bindingResult);
    }

    private boolean validateNameNotEmpty(PetDto petDto, BindingResult bindingResult) {
        if (petDto.getName().isEmpty()) {
            FieldError fieldError = new FieldError("petDto", "name",
                    "Name should not be empty!");
            bindingResult.addError(fieldError);
            return true;
        }
        return false;
    }

    private void validateNameFormat(PetDto petDto, BindingResult bindingResult) {
        if (!petDto.getName().matches("^[a-zA-Z -]+$")) {
            FieldError fieldError = new FieldError("petDto", "name",
                    "Wrong format");
            bindingResult.addError(fieldError);
        }
    }

    private void validateSpecie(PetDto petDto, BindingResult bindingResult) {
        validateSpecieFormat(petDto, bindingResult);
    }

    private void validateSpecieFormat(PetDto petDto, BindingResult bindingResult) {
        try {
            Specie.valueOf(petDto.getSpecie());
        } catch (Exception e) {
            FieldError fieldError = new FieldError("petDto", "specie",
                    "Wrong format!");
            bindingResult.addError(fieldError);
        }
    }

    private void validateBreed(PetDto petDto, BindingResult bindingResult) {
        if (validateBreedNotEmpty(petDto, bindingResult)) return;
        validateBreedFormat(petDto, bindingResult);
    }

    private boolean validateBreedNotEmpty(PetDto petDto, BindingResult bindingResult) {
        if (petDto.getBreed().isEmpty()) {
            FieldError fieldError = new FieldError("petDto", "breed",
                    "Breed should not be empty!");
            bindingResult.addError(fieldError);
            return true;
        }
        return false;
    }

    private void validateBreedFormat(PetDto petDto, BindingResult bindingResult) {
        if (!petDto.getBreed().matches("^[a-zA-Z -]+$")) {
            FieldError fieldError = new FieldError("petDto", "breed",
                    "Wrong format");
            bindingResult.addError(fieldError);
        }
    }

    private void validateGender(PetDto petDto, BindingResult bindingResult) {
        validateGenderFormat(petDto, bindingResult);
    }

    private void validateGenderFormat(PetDto petDto, BindingResult bindingResult) {
        try {
            Gender.valueOf(petDto.getGender());
        } catch (Exception e) {
            FieldError fieldError = new FieldError("petDto", "gender",
                    "Wrong format!");
            bindingResult.addError(fieldError);
        }
    }

    private void validateDateOfBirth(PetDto petDto, BindingResult bindingResult) {
        validateDateOfBirthNotEmpty(petDto, bindingResult);
    }

    private void validateDateOfBirthNotEmpty(PetDto petDto, BindingResult bindingResult) {
        if (petDto.getDateOfBirth().isEmpty()) {
            FieldError fieldError = new FieldError("petDto", "dateOfBirth",
                    "Date Of Birth should not be empty");
            bindingResult.addError(fieldError);
        }
    }

}
