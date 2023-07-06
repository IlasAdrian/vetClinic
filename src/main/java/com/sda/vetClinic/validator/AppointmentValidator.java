package com.sda.vetClinic.validator;

import com.sda.vetClinic.dto.AppointmentDto;
import com.sda.vetClinic.enums.Type;
import com.sda.vetClinic.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class AppointmentValidator {
    @Autowired
    PetService petService;

    public void validate(AppointmentDto appointmentDto, BindingResult bindingResult, String authEmail) {
        validatePet(appointmentDto, bindingResult, authEmail);
        validateType(appointmentDto, bindingResult);
        validateDescription(appointmentDto, bindingResult);
    }

    private void validatePet(AppointmentDto appointmentDto, BindingResult bindingResult, String authEmail) {
        if(validatePetNotEmpty(appointmentDto,bindingResult)) return;
        petIsPresent(appointmentDto, bindingResult, authEmail);
    }
    private boolean validatePetNotEmpty(AppointmentDto appointmentDto, BindingResult bindingResult) {
        if (appointmentDto.getDescription().isEmpty()) {
            FieldError fieldError = new FieldError("appointmentDto", "description",
                    "Description should not be empty!");
            bindingResult.addError(fieldError);
            return true;
        }
        return false;
    }

    private void petIsPresent(AppointmentDto appointmentDto, BindingResult bindingResult, String authEmail) {
        if(!petService.getPetDtoById(appointmentDto.getPetId()).getOwnerEmail().equals(authEmail)){
            FieldError fieldError = new FieldError("appointmentDto", "pet",
                    "Wrong Pet!");
            bindingResult.addError(fieldError);
        }

    }

    private void validateType(AppointmentDto appointmentDto, BindingResult bindingResult) {
        validateTypeFormat(appointmentDto, bindingResult);
    }

    private void validateTypeFormat(AppointmentDto appointmentDto, BindingResult bindingResult) {
        try {
            Type.valueOf(appointmentDto.getType());
        } catch (Exception e) {
            FieldError fieldError = new FieldError("appointmentDto", "type",
                    "Wrong format!");
            bindingResult.addError(fieldError);
        }
    }

    private void validateDescription(AppointmentDto appointmentDto, BindingResult bindingResult) {
        validateDescriptionNotEmpty(appointmentDto, bindingResult);
    }
    private void validateDescriptionNotEmpty(AppointmentDto appointmentDto, BindingResult bindingResult) {
        if (appointmentDto.getDescription().isEmpty()) {
            FieldError fieldError = new FieldError("appointmentDto", "description",
                    "Description should not be empty!");
            bindingResult.addError(fieldError);
        }
    }
}
