package com.sda.vetClinic.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;
    private String dateOfBirth;
    private String role;
}
