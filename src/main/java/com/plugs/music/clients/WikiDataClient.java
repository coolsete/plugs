package com.plugs.music.clients;

import com.plugs.music.model.responses.Response;
import com.plugs.music.model.responses.WikiDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
public class WikiDataClient extends BasicClient {
    private static final String RESOURCE_URL = "/wiki/Special:EntityData/%s.json";

    public WikiDataClient(WebClient webClient) {
        super(webClient);
    }

    public Mono<Response> getEntityInfo(Mono<String> entityId) {
        log.debug("Getting an entity");
        return entityId.flatMap(id -> get(String.format(RESOURCE_URL, id), WikiDataResponse.class));
    }
}
