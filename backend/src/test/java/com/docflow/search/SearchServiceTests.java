package com.docflow.search;

import java.net.URI;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SearchServiceTests {
    @Test
    void filtersConnectorsAndPreservesSourceReferences() {
        var slack = mock(SearchConnector.class);
        var git = mock(SearchConnector.class);
        when(slack.source()).thenReturn(AssetSource.SLACK);
        when(git.source()).thenReturn(AssetSource.GIT);
        var result = new SearchResult("1", AssetSource.SLACK, "Onboarding", "Welcome",
                URI.create("https://example.com/message/1"));
        when(slack.search("onboarding")).thenReturn(List.of(result));
        var service = new SearchService(List.of(slack, git));

        var response = service.search("onboarding", Set.of(AssetSource.SLACK));

        assertThat(response.connectedSources()).containsExactly(AssetSource.SLACK);
        assertThat(response.results()).containsExactly(result);
        verify(git, never()).search(anyString());

        when(git.search("onboarding")).thenReturn(List.of());
        assertThat(service.search("onboarding", Set.of()).connectedSources())
                .containsExactly(AssetSource.SLACK, AssetSource.GIT);
        verify(git).search("onboarding");
    }
}
