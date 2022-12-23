package com.akyagmur.swe573.service;

import com.akyagmur.swe573.service.dto.PostDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.akyagmur.swe573.domain.Post}.
 */
public interface PostService {
    /**
     * Save a post.
     *
     * @param postDTO the entity to save.
     * @return the persisted entity.
     */
    PostDTO save(PostDTO postDTO);

    /**
     * Updates a post.
     *
     * @param postDTO the entity to update.
     * @return the persisted entity.
     */
    PostDTO update(PostDTO postDTO);

    /**
     * Partially updates a post.
     *
     * @param postDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PostDTO> partialUpdate(PostDTO postDTO);

    /**
     * Get all the posts.
     *
     * @return the list of entities.
     */
    List<PostDTO> findAll();

    /**
     * Get the "id" post.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostDTO> findOne(Long id);

    /**
     * Delete the "id" post.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the post corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    List<PostDTO> search(String query);
}
