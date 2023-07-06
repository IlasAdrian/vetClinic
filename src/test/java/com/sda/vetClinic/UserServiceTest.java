package com.sda.vetClinic;


import com.sda.vetClinic.dto.UserDto;
import com.sda.vetClinic.entity.User;
import com.sda.vetClinic.mapper.UserMapper;
import com.sda.vetClinic.repository.UserRepository;
import com.sda.vetClinic.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceTest  {

    private UserService userService;
    @Mock
    private UserMapper userMapper = mock(UserMapper.class);
    @Mock
    private UserRepository userRepository = mock(UserRepository.class);


    @BeforeEach
    public void setUp(){
        userService = new UserService();

        userService.setUserRepository(userRepository);
        userService.setUserMapper(userMapper);
    }
    @Test
    public void shouldAddUser(){
        //SetUp
        UserDto userDto = UserDto.builder()
                .firstname("Adrian")
                .lastname("Ilas")
                .email("adrian@yahoo.com")
                .build();
        User user = User.builder()
                .firstname("Adrian")
                .lastname("Ilas")
                .email("adrian@yahoo.com")
                .build();
        when(userMapper.map(userDto)).thenReturn(user);

        //test
        userService.addUser(userDto);

        //assert
        verify(userMapper, times(1)).map(userDto);
        verify(userRepository, times(1)).save(user);
    }
}
