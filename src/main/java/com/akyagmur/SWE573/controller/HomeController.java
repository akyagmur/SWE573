package com.akyagmur.SWE573.controller;

import com.akyagmur.SWE573.dto.UserDto;
import com.akyagmur.SWE573.entity.User;
import com.akyagmur.SWE573.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

    @GetMapping("home")
    public String home() {
        return "Hello World";
    }

    @PostMapping("store")
    public UserDto store(@RequestBody UserDto userDto) {
        return userService.save(userDto);
                                          }
}
