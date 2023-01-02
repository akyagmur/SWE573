package com.akyagmur.swe573.repository;

import com.akyagmur.swe573.domain.Post;
import com.akyagmur.swe573.service.dto.PostDTO;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Post entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.title like %:searchTerm% OR p.content like %:searchTerm% OR p.url like %:searchTerm%")
    List<Post> searchAllFields(@Param("searchTerm") String searchTerm);

    // where is_private = false
    @Query("SELECT p FROM Post p WHERE p.is_private = false")
    Page<Post> findAllPublicPosts(Pageable pageable);

    // users own posts
    @Query("SELECT p FROM Post p WHERE p.created_by = :userId")
    List<Post> findAllPostsByUserId(@Param("userId") Long userId);

    // users own posts
    @Query("SELECT p FROM Post p WHERE p.created_by = :userId")
    Page<Post> findAllPostsByUserId(@Param("userId") Long userId, Pageable pageable);

    // users own posts
    @Query("SELECT p FROM Post p WHERE p.is_private = false and p.created_by = :userId")
    Page<Post> findAllPublicPostsByUserId(@Param("userId") Long userId, Pageable pageable);

    // user bookmarked posts
    @Query(value = "SELECT p.* FROM post p WHERE p.id IN (SELECT b.post_id FROM rel_post__bookmark b WHERE b.user_id = ?1)", nativeQuery = true)
    Page<Post> findAllBookmarkedPostsByUserId(@Param("userId") Long userId, Pageable pageable);

    // user liked posts
    @Query(value = "SELECT p.* FROM post p WHERE p.id IN (SELECT l.post_id FROM rel_post__like l WHERE l.user_id = ?1)", nativeQuery = true)
    Page<Post> findAllLikedPostsByUserId(@Param("userId") Long userId, Pageable pageable);

    // posts of a tag
    @Query(value = "SELECT p.* FROM post p WHERE p.id IN (SELECT t.post_id FROM rel_post__tag t WHERE t.tag_id = ?1)", nativeQuery = true)
    Page<Post> findAllPostsByTagId(@Param("tagId") Long tagId, Pageable pageable);
}
