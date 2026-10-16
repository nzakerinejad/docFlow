package com.docflow.search;

import java.util.List;

public record SearchResponse(String query, List<AssetSource> connectedSources, List<SearchResult> results) {}
