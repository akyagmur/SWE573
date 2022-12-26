package com.akyagmur.swe573.web.rest;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.akyagmur.swe573.domain.User;
import com.akyagmur.swe573.repository.UserRepository;
import com.akyagmur.swe573.service.EmailAlreadyUsedException;
import com.akyagmur.swe573.service.UserService;
import com.akyagmur.swe573.service.dto.EditProfileDTO;
import com.akyagmur.swe573.service.dto.UserDTO;
import com.akyagmur.swe573.web.rest.errors.LoginAlreadyUsedException;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class ProfileResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);
    private UserRepository userRepository;
    private UserService userService;

    public ProfileResource(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/profile/update")
    public ResponseEntity<EditProfileDTO> updateUser(@Valid @RequestBody EditProfileDTO userDTO) {
        log.debug("REST request to update User : {}", userDTO);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<EditProfileDTO> updatedUser = userService.updateUser(userDTO);

        return ResponseUtil.wrapOrNotFound(updatedUser);
    }
}
