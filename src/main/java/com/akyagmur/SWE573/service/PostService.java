package com.akyagmur.swe573.service;

import com.akyagmur.swe573.service.dto.PostDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Get all the contents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostDTO> findAll(Pageable pageable);

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

    /**
     * Get all public posts.
     *
     * @param pageable the pagination information.
     * @return the list of public entities.
     */
    Page<PostDTO> findAllPublicPosts(Pageable pageable);

    /**
     * Get all posts of a user.
     *
     * @param pageable the pagination information.
     * @param userId the id of the user.
     */
    Page<PostDTO> findAllPostsOfUser(Pageable pageable, Long userId);

    /**
     * Get all public posts of a user.
     *
     * @param pageable the pagination information.
     * @param userId the id of the user.
     */
    Page<PostDTO> findAllPublicPostsOfUser(Pageable pageable, Long userId);

    /**
     * Get all liked posts of a user.
     *
     * @param userId the id of the user.
     */
    Page<PostDTO> findAllLikedPostsOfUser(Pageable pageable, Long userId);

    /**
     * Get all bookmarked posts of a user.
     *
     * @param userId the id of the user.
     */
    Page<PostDTO> findAllBookmarkedPostsOfUser(Pageable pageable, Long userId);

    /**
     * Find all posts by tag
     *
     * @param pageable the pagination information.
     * @param tagId the id of the tag.
     */
    Page<PostDTO> findAllPostsByTag(Pageable pageable, Long tagId);
}
