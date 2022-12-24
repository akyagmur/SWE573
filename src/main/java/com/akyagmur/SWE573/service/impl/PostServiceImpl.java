package com.akyagmur.swe573.service.impl;

import com.akyagmur.swe573.domain.Post;
import com.akyagmur.swe573.repository.PostRepository;
import com.akyagmur.swe573.service.PostService;
import com.akyagmur.swe573.service.UserService;
import com.akyagmur.swe573.service.dto.PostDTO;
import com.akyagmur.swe573.service.mapper.PostMapper;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Implementation for managing {@link Post}.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final UserService userService;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, UserService userService) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userService = userService;
    }

    @Override
    public PostDTO save(PostDTO postDTO) {
        log.debug("Request to save Post : {}", postDTO);
        postDTO.setCreated_by(userService.getUserWithAuthorities().get().getId());
        postDTO.setCreated_at(ZonedDateTime.now());
        Post post = postMapper.toEntity(postDTO);
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    public PostDTO update(PostDTO postDTO) {
        log.debug("Request to update Post : {}", postDTO);
        Post post = postMapper.toEntity(postDTO);
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    public Optional<PostDTO> partialUpdate(PostDTO postDTO) {
        log.debug("Request to partially update Post : {}", postDTO);

        return postRepository
                .findById(postDTO.getId())
                .map(existingPost -> {
                    postMapper.partialUpdate(existingPost, postDTO);

                    return existingPost;
                })
                .map(postRepository::save)
                .map(postMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Posts");
        return postRepository.findAll(pageable).map(postMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PostDTO> findOne(Long id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findById(id).map(postMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.deleteById(id);
    }

    /**
     * Search for the post corresponding to the query.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PostDTO> search(String query) {
        log.debug("Request to search for a page of Posts for query {}", query);
        return postRepository.searchAllFields(query)
                .stream()
                .map(postMapper::toDto)
                .sorted((p1, p2) -> p2.getCreated_at().compareTo(p1.getCreated_at()))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
