package com.akyagmur.swe573.web.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import com.akyagmur.swe573.domain.User;
import com.akyagmur.swe573.repository.UserRepository;
import com.akyagmur.swe573.service.EmailAlreadyUsedException;
import com.akyagmur.swe573.service.PostService;
import com.akyagmur.swe573.service.UserService;
import com.akyagmur.swe573.service.dto.EditProfileDTO;
import com.akyagmur.swe573.service.dto.PostDTO;
import com.akyagmur.swe573.web.rest.errors.LoginAlreadyUsedException;
import org.springframework.data.domain.Sort;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class ProfileResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);
    private UserRepository userRepository;
    private UserService userService;
    private PostService postService;

    public ProfileResource(UserRepository userRepository, UserService userService, PostService postService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.postService = postService;
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

    @PostMapping("/profile/my-bookmarks")
    public ResponseEntity<List<PostDTO>> getMyBookmarks(
            @SortDefault(sort = "id", direction = Sort.Direction.DESC) @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        log.debug("REST request to get a page of Contents");
        Long userId = userService.getUserWithAuthorities().get().getId();
        Page<PostDTO> page = postService.findAllBookmarkedPostsOfUser(pageable, userId);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/profile/my-liked-posts")
    public ResponseEntity<List<PostDTO>> getMyLikedPosts(
            @SortDefault(sort = "id", direction = Sort.Direction.DESC) @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        log.debug("REST request to get a page of Contents");
        Long userId = userService.getUserWithAuthorities().get().getId();
        Page<PostDTO> page = postService.findAllLikedPostsOfUser(pageable, userId);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
