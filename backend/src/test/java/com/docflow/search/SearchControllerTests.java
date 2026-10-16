package com.docflow.search;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SearchControllerTests {
    @Autowired MockMvc mvc;

    @Test
    void returnsEmptyResultsUntilConnectorsAreConfigured() throws Exception {
        mvc.perform(get("/api/search").param("query", "  onboarding  ").param("sources", "SLACK,GIT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.query").value("onboarding"))
                .andExpect(jsonPath("$.connectedSources").isEmpty())
                .andExpect(jsonPath("$.results").isEmpty());
    }

    @Test
    void rejectsInvalidRequests() throws Exception {
        mvc.perform(get("/api/search")).andExpect(status().isBadRequest());
        mvc.perform(get("/api/search").param("query", "   ")).andExpect(status().isBadRequest());
        mvc.perform(get("/api/search").param("query", "x".repeat(1001))).andExpect(status().isBadRequest());
        mvc.perform(get("/api/search").param("query", "docs").param("sources", "UNKNOWN"))
                .andExpect(status().isBadRequest());
    }
}
