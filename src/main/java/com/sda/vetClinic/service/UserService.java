package com.sda.vetClinic.service;

import com.sda.vetClinic.dto.UserDto;
import com.sda.vetClinic.entity.User;
import com.sda.vetClinic.mapper.UserMapper;
import com.sda.vetClinic.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter
@Getter
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    public void addUser(UserDto userDto){
        User user = userMapper.map(userDto);
        userRepository.save(user);
    }
}
