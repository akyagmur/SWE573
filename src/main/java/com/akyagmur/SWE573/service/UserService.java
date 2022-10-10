package com.akyagmur.SWE573.service;

import com.akyagmur.SWE573.dto.UserDto;
import com.akyagmur.SWE573.entity.User;

import java.util.List;

public interface UserService {
    UserDto save(UserDto user);
    User findByName(String name);
    List<User> findAllByBirthDateBefore(String name, String password);
    User findAllByNameAndBirthDateIsAfter(String name, String birthDate);
}
