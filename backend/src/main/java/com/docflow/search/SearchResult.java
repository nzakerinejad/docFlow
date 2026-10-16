package com.docflow.search;

import java.net.URI;

public record SearchResult(String id, AssetSource source, String title, String snippet, URI url) {}
