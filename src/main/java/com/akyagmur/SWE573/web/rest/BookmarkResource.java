package com.akyagmur.swe573.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akyagmur.swe573.domain.Post;
import com.akyagmur.swe573.domain.User;
import com.akyagmur.swe573.repository.PostRepository;
import com.akyagmur.swe573.repository.UserRepository;
import com.akyagmur.swe573.service.PostService;
import com.akyagmur.swe573.service.UserService;
import com.akyagmur.swe573.service.dto.PostDTO;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

/**
 * BookmarkResource controller
 */
@RestController
@RequestMapping("/api")
public class BookmarkResource {

    private final Logger log = LoggerFactory.getLogger(BookmarkResource.class);

    private final PostService postService;

    private final PostRepository postRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    public BookmarkResource(PostService postService, PostRepository postRepository, UserService userService, UserRepository userRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * Add post to bookmark
     */
    @PostMapping("/bookmark/{id}")
    public PostDTO bookmark(@RequestBody PostDTO post) {
        log.debug("REST request to bookmark a post with id {}", post.getId());
        Post postToBookmark = postRepository.findById(post.getId()).get();
        User user = userRepository.findById(userService.getUserWithAuthorities().get().getId()).get();
        var bookmarks = user.getBookmarks();

        if(bookmarks.contains(postToBookmark)) {
            bookmarks.remove(postToBookmark);
            user.setBookmarks(bookmarks);
            userRepository.save(user);
            return post;
        }

        bookmarks.add(postToBookmark);
        user.setBookmarks(bookmarks);
        userRepository.save(user);

        return post;
    }
}
