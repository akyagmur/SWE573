package com.akyagmur.swe573.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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
public class LikeResource {

    private final Logger log = LoggerFactory.getLogger(BookmarkResource.class);

    private final PostRepository postRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    public LikeResource(PostRepository postRepository, UserService userService, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * Like a post
     *
     * @param post
     * @return
     */
    @PostMapping("/like/{id}")
    public PostDTO bookmark(@RequestBody PostDTO post) {
        log.debug("REST request to like a post with id {}", post.getId());
        Post postToLike = postRepository.findById(post.getId()).get();
        User user = userRepository.findById(userService.getUserWithAuthorities().get().getId()).get();
        var likes = user.getLikes();

        if(likes.contains(postToLike)) {
            likes.remove(postToLike);
            user.setLikes(likes);
            userRepository.save(user);
            return post;
        }

        likes.add(postToLike);
        user.setLikes(likes);
        userRepository.save(user);

        return post;
    }

    /**
     * Get user's liked post ids
     *
     * @param post
     * @return
     */
    @PostMapping("/user-likes")
    public ResponseEntity<List<Long>> getUserLikes() {
        log.debug("REST request to get user's liked posts");
        User user = userRepository.findById(userService.getUserWithAuthorities().get().getId()).get();
        var likes = user.getLikes();
        List<Long> ids = likes.stream().map(Post::getId).toList();
        return ResponseEntity.ok(ids);
    }
}
