package com.sda.vetClinic.repository;

import com.sda.vetClinic.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
    User findUserByEmail(String email);
}
