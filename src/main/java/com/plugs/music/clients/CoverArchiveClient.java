package com.plugs.music.clients;

import com.plugs.music.model.responses.CoverResponse;
import com.plugs.music.model.responses.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
public class CoverArchiveClient extends BasicClient {

    private static final String RESOURCE_URL = "/release-group/%s";

    public CoverArchiveClient(WebClient webClient) {
        super(webClient);
    }

    public Mono<Response> getImages(Mono<String> resourceId) {
        return resourceId.flatMap(resource -> get(String.format(RESOURCE_URL, resource), CoverResponse.class));
    }
}
