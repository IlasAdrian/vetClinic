package com.sda.vetClinic.service;

import com.sda.vetClinic.dto.UserDto;
import com.sda.vetClinic.entity.User;
import com.sda.vetClinic.mapper.UserMapper;
import com.sda.vetClinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    public void addUser(UserDto userDto){
        User user = userMapper.map(userDto);
        userRepository.save(user);
    }
    public List<UserDto> getAllUsersDto() {
        List<UserDto> usersDto = new ArrayList<>();
        Iterable<User> userIterable = userRepository.findAll();
        for (User user : userIterable) {
            UserDto userDto = userMapper.map(user);
            usersDto.add(userDto);
        }
        return usersDto;
    }
    public Optional<UserDto> getUserDtoByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return Optional.empty();
        }
        User user = optionalUser.get();
        UserDto userDto = userMapper.map(user);
        return Optional.of(userDto);
    }
}
