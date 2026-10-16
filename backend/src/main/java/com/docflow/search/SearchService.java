package com.docflow.search;

import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private final List<SearchConnector> connectors;

    public SearchService(List<SearchConnector> connectors) {
        this.connectors = List.copyOf(connectors);
    }

    public SearchResponse search(String query, Set<AssetSource> sources) {
        var selected = connectors.stream()
                .filter(connector -> sources.isEmpty() || sources.contains(connector.source()))
                .toList();
        var results = selected.stream().flatMap(connector -> connector.search(query).stream()).toList();
        return new SearchResponse(query, selected.stream().map(SearchConnector::source).distinct().toList(), results);
    }
}
