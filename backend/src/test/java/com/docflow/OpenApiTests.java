package com.docflow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiTests {
    @Autowired MockMvc mvc;

    @Test
    void documentsSearchEndpoint() throws Exception {
        mvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("docFlow API"))
                .andExpect(jsonPath("$.paths['/api/search'].get.summary").value("Search company assets"))
                .andExpect(jsonPath("$.paths['/api/search'].get.parameters[?(@.name == 'query')].required").value(true))
                .andExpect(jsonPath("$.paths['/api/search'].get.parameters[1].schema.items.enum",
                        hasItems("CONFLUENCE", "SLACK", "GIT")));
    }

    @Test
    void servesSwaggerUiAndConfiguration() throws Exception {
        mvc.perform(get("/swagger-ui.html"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/swagger-ui/index.html"));
        mvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Swagger UI")));
        mvc.perform(get("/v3/api-docs/swagger-config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("/v3/api-docs"))
                .andExpect(jsonPath("$.tryItOutEnabled").value(true));
    }
}
