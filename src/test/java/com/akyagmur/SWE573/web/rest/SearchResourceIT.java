package com.akyagmur.swe573.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.akyagmur.swe573.IntegrationTest;
import com.akyagmur.swe573.service.PostService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Test class for the SearchResource REST controller.
 *
 * @see SearchResource
 */
@IntegrationTest
class SearchResourceIT {

    private MockMvc restMockMvc;

    @Autowired
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        SearchResource searchResource = new SearchResource(postService);
        restMockMvc = MockMvcBuilders.standaloneSetup(searchResource).build();
    }

    /**
     * Test search
     */
    @Test
    void testSearch() throws Exception {
        restMockMvc.perform(post("/api/search/search")).andExpect(status().isOk());
    }

    /**
     * Test searchPosts
     */
    @Test
    void testSearchPosts() throws Exception {
        restMockMvc.perform(get("/api/search/search-posts")).andExpect(status().isOk());
    }
}
