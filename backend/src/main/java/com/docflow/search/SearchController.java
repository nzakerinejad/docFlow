package com.docflow.search;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@Tag(name = "Search", description = "Search company assets across connected platforms")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    @Operation(summary = "Search company assets",
            description = "Returns results from connected platforms. No platforms are connected yet, so results are currently empty.")
    public SearchResponse search(
            @Parameter(description = "Search text (1–1,000 characters, must not be blank)", example = "onboarding")
            @RequestParam @NotBlank @Size(max = 1000) String query,
            @Parameter(description = "Optional platforms to search; omit to search all connected platforms")
            @RequestParam(required = false) Set<AssetSource> sources) {
        return searchService.search(query.strip(), sources == null ? Set.of() : sources);
    }
}
