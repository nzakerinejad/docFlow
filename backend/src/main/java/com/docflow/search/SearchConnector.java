package com.docflow.search;

import java.util.List;

/** Implement as a Spring bean for each platform, enforcing the caller's source permissions. */
public interface SearchConnector {
    AssetSource source();
    List<SearchResult> search(String query);
}
