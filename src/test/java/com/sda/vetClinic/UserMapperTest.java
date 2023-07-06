package com.sda.vetClinic;

import com.sda.vetClinic.dto.UserDto;
import com.sda.vetClinic.entity.User;
import com.sda.vetClinic.enums.Role;
import com.sda.vetClinic.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserMapperTest {
    private UserMapper userMapper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
    @BeforeEach
    public void setUp(){
        userMapper = new UserMapper();

        userMapper.setBCryptPasswordEncoder(bCryptPasswordEncoder);
    }

    @Test
    public void shouldMapUserDto(){
        //setUp
        User userExpected = User.builder()
                .firstname("Adrian")
                .lastname("Ilas")
                .email("adrian@yahoo.com")
                .password("password encoded")
                .phoneNumber("0751559881")
                .dateOfBirth(LocalDate.of(2002,2,10))
                .role(Role.ROLE_OWNER)
                .build();
        UserDto userDto = UserDto.builder()
                .firstname("Adrian")
                .lastname("Ilas")
                .email("adrian@yahoo.com")
                .password("password")
                .phoneNumber("0751559881")
                .dateOfBirth(LocalDate.of(2002,2,10).toString())
                .role(Role.ROLE_OWNER.name())
                .build();
        when(bCryptPasswordEncoder.encode("password")).thenReturn("password encoded");

        //test
        User userActual = userMapper.map(userDto);

        //assert
        assertEquals(userExpected.getFirstname(), userActual.getFirstname());
        assertEquals(userExpected.getLastname(), userActual.getLastname());
        assertEquals(userExpected.getEmail(), userActual.getEmail());
        assertEquals(userExpected.getPassword(), userActual.getPassword());
        assertEquals(userExpected.getPhoneNumber(), userActual.getPhoneNumber());
        assertEquals(userExpected.getDateOfBirth(), userActual.getDateOfBirth());
        assertEquals(userExpected.getRole(), userActual.getRole());

    }
}
