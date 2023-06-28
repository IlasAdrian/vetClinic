package com.sda.vetClinic.controller;

import com.sda.vetClinic.dto.UserDto;
import com.sda.vetClinic.service.UserService;
import com.sda.vetClinic.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RestControllerExample {
    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/api/users")
    public List<UserDto> getAllUserDtoList(){
        return userService.getAllUsersDto();
    }

    @PostMapping("/api/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto){
        System.out.println(userDto);
        if(userValidator.validateUniqueEmail(userDto)){
            return ResponseEntity.badRequest().body("not unique");
        }
        userService.addUser(userDto);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/api/user/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable(name = "email") String email){
        Optional<UserDto> optionalUserDto = userService.getUserDtoByEmail(email);
        if(optionalUserDto.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        UserDto userDto = optionalUserDto.get();
        return ResponseEntity.ok(userDto);
    }
}