package com.akyagmur.swe573.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akyagmur.swe573.service.PostService;
import com.akyagmur.swe573.service.dto.PostDTO;

/**
 * SearchResource controller
 */
@RestController
@RequestMapping("/api")
public class SearchResource {

    private final Logger log = LoggerFactory.getLogger(SearchResource.class);

    private final PostService postService;

    public SearchResource(PostService postService) {
        this.postService = postService;
    }

    /**
     * Get search
     */
    @GetMapping("/search")
    public List<PostDTO> search(@RequestParam String query) {
        log.debug("REST request to search for a page of Posts for query {}", query);
        return postService.search(query);
    }
}
