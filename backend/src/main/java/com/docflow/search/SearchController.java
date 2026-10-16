package com.docflow.search;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public SearchResponse search(
            @RequestParam @NotBlank @Size(max = 1000) String query,
            @RequestParam(required = false) Set<AssetSource> sources) {
        return searchService.search(query.strip(), sources == null ? Set.of() : sources);
    }
}
