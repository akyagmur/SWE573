package com.akyagmur.SWE573.service.impl;

import com.akyagmur.SWE573.dto.UserDto;
import com.akyagmur.SWE573.entity.User;
import com.akyagmur.SWE573.repo.UserRepository;
import com.akyagmur.SWE573.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto save(UserDto user) {
        User userEntity = new User();
        userEntity.setName(user.getName());
        userEntity.setPassword(user.getPassword());
        userEntity = userRepository.save(userEntity);
        return new UserDto(userEntity.getId(), userEntity.getName(), userEntity.getPassword());
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name).orElseThrow(() -> new NullPointerException("User not found"));
    }

    @Override
    public List<User> findAllByBirthDateBefore(String name, String password) {
        return null;
    }

    @Override
    public User findAllByNameAndBirthDateIsAfter(String name, String birthDate) {
        return null;
    }
}
