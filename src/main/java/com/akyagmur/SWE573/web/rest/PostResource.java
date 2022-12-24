package com.akyagmur.swe573.web.rest;

import com.akyagmur.swe573.repository.PostRepository;
import com.akyagmur.swe573.service.PostService;
import com.akyagmur.swe573.service.dto.PostDTO;
import com.akyagmur.swe573.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpHeaders;

import java.util.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.akyagmur.swe573.domain.Post}.
 */
@RestController
@RequestMapping("/api")
public class PostResource {

    private final Logger log = LoggerFactory.getLogger(PostResource.class);

    private static final String ENTITY_NAME = "post";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostService postService;

    private final PostRepository postRepository;

    public PostResource(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    /**
     * {@code POST  /posts} : Create a new post.
     *
     * @param postDTO the postDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new postDTO, or with status {@code 400 (Bad Request)} if the
     *         post has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/posts")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) throws URISyntaxException {
        log.debug("REST request to save Post : {}", postDTO);
        if (postDTO.getId() != null) {
            throw new BadRequestAlertException("A new post cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ;
        PostDTO result = postService.save(postDTO);
        return ResponseEntity
                .created(new URI("/api/posts/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                        result.getId().toString()))
                .body(postDTO);
    }

    /**
     * {@code PUT  /posts/:id} : Updates an existing post.
     *
     * @param id      the id of the postDTO to save.
     * @param postDTO the postDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated postDTO,
     *         or with status {@code 400 (Bad Request)} if the postDTO is not valid,
     *         or with status {@code 500 (Internal Server Error)} if the postDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDTO> updatePost(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody PostDTO postDTO) throws URISyntaxException {
        log.debug("REST request to update Post : {}, {}", id, postDTO);
        if (postDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PostDTO result = postService.update(postDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                        postDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /posts/:id} : Partial updates given fields of an existing post,
     * field will ignore if it is null
     *
     * @param id      the id of the postDTO to save.
     * @param postDTO the postDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated postDTO,
     *         or with status {@code 400 (Bad Request)} if the postDTO is not valid,
     *         or with status {@code 404 (Not Found)} if the postDTO is not found,
     *         or with status {@code 500 (Internal Server Error)} if the postDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/posts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PostDTO> partialUpdatePost(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody PostDTO postDTO) throws URISyntaxException {
        log.debug("REST request to partial update Post partially : {}, {}", id, postDTO);
        if (postDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PostDTO> result = postService.partialUpdate(postDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postDTO.getId().toString()));
    }

    /**
     * {@code GET  /posts} : get all the posts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of posts in body.
     */
    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getAllPosts(@PageableDefault(size = 5) Pageable pageable) {
        log.debug("REST request to get a page of Contents");
        Page<PostDTO> page = postService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /posts/:id} : get the "id" post.
     *
     * @param id the id of the postDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the postDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
        log.debug("REST request to get Post : {}", id);
        Optional<PostDTO> postDTO = postService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postDTO);
    }

    /**
     * {@code DELETE  /posts/:id} : delete the "id" post.
     *
     * @param id the id of the postDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        log.debug("REST request to delete Post : {}", id);
        postService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }

    @PostMapping("/posts/fetch-meta")
    public Map<String, String> fetchMetaOfUrl(@RequestBody Map<String, String> payload) throws java.io.IOException {

        log.debug("REST request to fetch meta of url : {}", payload);

        String url = payload.get("url");
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }

        log.debug("REST request to fetch meta of url : {}", url);

        Document document = Jsoup.connect(url).get();
        String title = getMetaTagContent(document, "meta[name=title]");
        String desc = getMetaTagContent(document, "meta[name=description]");
        String ogTitle = getMetaTagContent(document, "meta[property=og:title]");
        String ogDesc = getMetaTagContent(document, "meta[property=og:description]");
        String ogImage = getMetaTagContent(document, "meta[property=og:image]");
        String ogImageAlt = getMetaTagContent(document, "meta[property=og:image:alt]");

        Map<String, String> meta = new HashMap<>();
        meta.put("title", title);
        meta.put("description", desc);
        meta.put("ogTitle", ogTitle);
        meta.put("ogDescription", ogDesc);
        meta.put("ogImage", ogImage);
        meta.put("ogImageAlt", ogImageAlt);

        log.debug("REST response to fetch meta of ogImage : {}", meta.get("ogImage"));

        return meta;
    }

    /**
     * Returns the given meta tag content
     *
     * @param document
     * @return the meta tag content
     */
    private String getMetaTagContent(Document document, String cssQuery) {
        Element elm = document.select(cssQuery).first();
        if (elm != null) {
            return elm.attr("content");
        }
        return "";
    }
}
