package com.plugs.music.clients;

import com.plugs.music.model.responses.Response;
import com.plugs.music.model.responses.WikipediaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class WikipediaClient extends BasicClient {

    private static final String RESOURCE_URL = "/w/api.php?action=query&format=json&prop=extracts&exintro=true&redirects=true&titles=%s";

    public WikipediaClient(WebClient webClient) {
        super(webClient);
    }

    public Mono<Response> getWikipediaInfo(Mono<String> title) {
        log.debug("Getting wikipedia description");
        return title.flatMap(tl -> get(String.format(RESOURCE_URL, tl), WikipediaResponse.class));
    }
}
