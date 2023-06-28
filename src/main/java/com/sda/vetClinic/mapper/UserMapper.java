package com.sda.vetClinic.mapper;

import com.sda.vetClinic.dto.UserDto;
import com.sda.vetClinic.entity.User;
import com.sda.vetClinic.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserMapper {
    @Autowired
     private BCryptPasswordEncoder bCryptPasswordEncoder;
    public User map(UserDto userDto){
        String passwordEncoded = bCryptPasswordEncoder.encode(userDto.getPassword());
        return User.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .password(passwordEncoded)
                .phoneNumber(userDto.getPhoneNumber())
                .dateOfBirth(LocalDate.parse(userDto.getDateOfBirth()))
                .role(Role.valueOf(userDto.getRole()))
                .build();
    }
    public UserDto map(User user){
        return UserDto.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth().toString())
                .build();
    }
}
