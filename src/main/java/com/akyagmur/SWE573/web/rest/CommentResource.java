package com.akyagmur.swe573.web.rest;

import com.akyagmur.swe573.domain.Comment;
import com.akyagmur.swe573.domain.Post;
import com.akyagmur.swe573.repository.CommentRepository;
import com.akyagmur.swe573.repository.PostRepository;
import com.akyagmur.swe573.service.CommentService;
import com.akyagmur.swe573.service.PostService;
import com.akyagmur.swe573.service.UserService;
import com.akyagmur.swe573.service.dto.CommentDTO;
import com.akyagmur.swe573.service.dto.PostDTO;
import com.akyagmur.swe573.web.rest.errors.BadRequestAlertException;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.akyagmur.swe573.domain.Comment}.
 */
@RestController
@RequestMapping("/api")
public class CommentResource {

    private final Logger log = LoggerFactory.getLogger(CommentResource.class);

    private static final String ENTITY_NAME = "comment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    private final PostService postService;

    private final PostRepository postRepository;

    private final UserService userService;

    public CommentResource(CommentService commentService, CommentRepository commentRepository, PostService postService, PostRepository postRepository, UserService userService) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.postRepository = postRepository;
        this.userService = userService;
    }

    /**
     * {@code POST  /comments} : Create a new comment.
     *
     * @param commentDTO the commentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentDTO, or with status {@code 400 (Bad Request)} if the comment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comments")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody Map<String, String> payload) throws URISyntaxException {
        String comment = payload.get("comment");
        Long postId = Long.parseLong(payload.get("postId"));
        Long userId =  userService.getUserWithAuthorities().get().getId();

        Comment commentEntity = new Comment();
        commentEntity.setComment(comment);
        commentEntity.setPostId(postId);
        commentEntity.setCreated_by(userId);
        commentEntity.setCreated_at(ZonedDateTime.now());
        commentRepository.save(commentEntity);

        CommentDTO result = commentService.findOne(commentEntity.getId()).get();

        return ResponseEntity
            .created(new URI("/api/comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comments/:id} : Updates an existing comment.
     *
     * @param id the id of the commentDTO to save.
     * @param commentDTO the commentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentDTO,
     * or with status {@code 400 (Bad Request)} if the commentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CommentDTO commentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Comment : {}, {}", id, commentDTO);
        if (commentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommentDTO result = commentService.update(commentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /comments/:id} : Partial updates given fields of an existing comment, field will ignore if it is null
     *
     * @param id the id of the commentDTO to save.
     * @param commentDTO the commentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentDTO,
     * or with status {@code 400 (Bad Request)} if the commentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the commentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the commentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/comments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommentDTO> partialUpdateComment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CommentDTO commentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Comment partially : {}, {}", id, commentDTO);
        if (commentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommentDTO> result = commentService.partialUpdate(commentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /comments} : get all the comments.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comments in body.
     */
    @GetMapping("/comments")
    public List<CommentDTO> getAllComments(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Comments");
        return commentService.findAll();
    }

    /**
     * {@code GET  /comments/:id} : get the "id" comment.
     *
     * @param id the id of the commentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long id) {
        log.debug("REST request to get Comment : {}", id);
        Optional<CommentDTO> commentDTO = commentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentDTO);
    }

    /**
     * {@code DELETE  /comments/:id} : delete the "id" comment.
     *
     * @param id the id of the commentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        log.debug("REST request to delete Comment : {}", id);
        commentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
