package com.sda.vetClinic.mapper;

import com.sda.vetClinic.dto.UserDto;
import com.sda.vetClinic.entity.User;
import com.sda.vetClinic.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
@Setter
@Getter
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
}
