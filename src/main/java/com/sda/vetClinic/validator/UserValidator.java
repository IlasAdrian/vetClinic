package com.sda.vetClinic.validator;

import com.sda.vetClinic.dto.UserDto;
import com.sda.vetClinic.entity.User;
import com.sda.vetClinic.enums.Role;
import com.sda.vetClinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class UserValidator {
    @Autowired
    UserRepository userRepository;

    public void validate(UserDto userDto, BindingResult bindingResult) {
        validateFirstname(userDto, bindingResult);
        validateLastname(userDto, bindingResult);
        validateEmail(userDto, bindingResult);
        validatePassword(userDto, bindingResult);
        validatePhoneNumber(userDto, bindingResult);
        validateDateOfBirth(userDto, bindingResult);
        validateRole(userDto, bindingResult);
    }

    private void validateFirstname(UserDto userDto, BindingResult bindingResult) {
        if (validateFirstnameNotEmpty(userDto, bindingResult)) return;
        validateFirstnameFormat(userDto, bindingResult);
    }

    private boolean validateFirstnameNotEmpty(UserDto userDto, BindingResult bindingResult) {
        if (userDto.getFirstname().isEmpty()) {
            FieldError fieldError = new FieldError("userDto", "firstname",
                    "Firstname should not be empty!");
            bindingResult.addError(fieldError);
            return true;
        }
        return false;
    }

    private void validateFirstnameFormat(UserDto userDto, BindingResult bindingResult) {
        if (!userDto.getFirstname().matches("^[a-zA-Z -]+$")) {
            FieldError fieldError = new FieldError("userDto", "firstName",
                    "Wrong format");
            bindingResult.addError(fieldError);
        }
    }

    private void validateLastname(UserDto userDto, BindingResult bindingResult) {
        if (validateLastnameNotEmpty(userDto, bindingResult)) return;
        validateLastnameFormat(userDto, bindingResult);
    }

    private boolean validateLastnameNotEmpty(UserDto userDto, BindingResult bindingResult) {
        if (userDto.getLastname().isEmpty()) {
            FieldError fieldError = new FieldError("userDto", "lastname",
                    "Lastname should not be empty!");
            bindingResult.addError(fieldError);
            return true;
        }
        return false;
    }

    private void validateLastnameFormat(UserDto userDto, BindingResult bindingResult) {
        if (!userDto.getLastname().matches("^[a-zA-Z -]+$")) {
            FieldError fieldError = new FieldError("userDto", "lastname",
                    "Wrong format");
            bindingResult.addError(fieldError);
        }
    }

    private void validateEmail(UserDto userDto, BindingResult bindingResult) {
        if (validateEmailNotEmpty(userDto, bindingResult)) return;
        if (validateEmailFormat(userDto, bindingResult)) return;
        validateEmailIsUnique(userDto, bindingResult);
    }

    private boolean validateEmailNotEmpty(UserDto userDto, BindingResult bindingResult) {
        if (userDto.getEmail().isEmpty()) {
            FieldError fieldError = new FieldError("userDto", "email",
                    "Email should not be empty");
            bindingResult.addError(fieldError);
            return true;
        }
        return false;
    }

    private boolean validateEmailFormat(UserDto userDto, BindingResult bindingResult) {
        if (!userDto.getEmail().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            FieldError fieldError = new FieldError("userDto", "email",
                    "Wrong format");
            bindingResult.addError(fieldError);
            return true;
        }
        return false;
    }

    private void validateEmailIsUnique(UserDto userDto, BindingResult bindingResult) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            FieldError fieldError = new FieldError("userDto", "email",
                    "E-mail is already in use");
            bindingResult.addError(fieldError);
        }
    }

    private void validatePassword(UserDto userDto, BindingResult bindingResult) {
        if (validatePasswordNotEmpty(userDto, bindingResult)) return;
        validatePasswordFormat(userDto, bindingResult);
    }

    private boolean validatePasswordNotEmpty(UserDto userDto, BindingResult bindingResult) {
        if (userDto.getPassword().isEmpty()) {
            FieldError fieldError = new FieldError("userDto", "password",
                    "Password should not be empty");
            bindingResult.addError(fieldError);
            return true;
        }
        return false;
    }

    private void validatePasswordFormat(UserDto userDto, BindingResult bindingResult) {
        if (userDto.getPassword().length() < 5) {
            FieldError fieldError = new FieldError("userDto", "password",
                    "Password too short");
            bindingResult.addError(fieldError);
        }
    }

    private void validatePhoneNumber(UserDto userDto, BindingResult bindingResult) {
        if (validatePhoneNumberNotEmpty(userDto, bindingResult)) return;
        validatePhoneNumberFormat(userDto, bindingResult);
    }

    private boolean validatePhoneNumberNotEmpty(UserDto userDto, BindingResult bindingResult) {
        if (userDto.getPhoneNumber().isEmpty()) {
            FieldError fieldError = new FieldError("userDto", "phoneNumber",
                    "PhoneNumber should not be empty");
            bindingResult.addError(fieldError);
            return true;
        }
        return false;
    }

    private void validatePhoneNumberFormat(UserDto userDto, BindingResult bindingResult) {
        if (!userDto.getPhoneNumber().matches("^\\d{10}$")) {
            FieldError fieldError = new FieldError("userDto", "phoneNumber",
                    "Wrong format");
            bindingResult.addError(fieldError);
        }
    }

    private void validateDateOfBirth(UserDto userDto, BindingResult bindingResult) {
        if (validateDateOfBirthNotEmpty(userDto, bindingResult)) return;
        validateDateOfBirthAge14(userDto, bindingResult);
    }

    private boolean validateDateOfBirthNotEmpty(UserDto userDto, BindingResult bindingResult) {
        if (userDto.getDateOfBirth().isEmpty()) {
            FieldError fieldError = new FieldError("userDto", "dateOfBirth",
                    "Date Of Birth should not be empty");
            bindingResult.addError(fieldError);
            return true;
        }
        return false;
    }

    private void validateDateOfBirthAge14(UserDto userDto, BindingResult bindingResult) {
        LocalDate dateOfBirth = LocalDate.parse(userDto.getDateOfBirth());
        if (dateOfBirth.isAfter(LocalDate.now().minusYears(14))) {
            FieldError fieldError = new FieldError("userDto", "dateOfBirth",
                    "Your age should be at least 14");
            bindingResult.addError(fieldError);
        }
    }

    private void validateRole(UserDto userDto, BindingResult bindingResult) {
//        if (validateRoleNotEmpty(userDto, bindingResult)) return;
        validateRoleFormat(userDto, bindingResult);
    }

//    private boolean validateRoleNotEmpty(UserDto userDto, BindingResult bindingResult) {
//        if (userDto.getDateOfBirth().isEmpty()) {
//            FieldError fieldError = new FieldError("userDto", "role",
//                    "Role should not be empty");
//            bindingResult.addError(fieldError);
//            return true;
//        }
//        return false;
//    }

    private void validateRoleFormat(UserDto userDto, BindingResult bindingResult) {
//        for (Role role : Role.values()) {
//            if (!userDto.getRole().equals(String.valueOf(role))){
//                FieldError fieldError = new FieldError("userDto", "role",
//                        "Wrong role format!");
//                bindingResult.addError(fieldError);
//
//            }
//        }
        try{
            Role.valueOf(userDto.getRole());
        } catch (Exception e){
            FieldError fieldError = new FieldError("userDto", "role",
                        "Wrong role format!");
                bindingResult.addError(fieldError);
        }
    }
    public Boolean validateUniqueEmail(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        return optionalUser.isPresent();
    }
}