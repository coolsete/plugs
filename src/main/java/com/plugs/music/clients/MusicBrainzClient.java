package com.plugs.music.clients;

import com.plugs.music.model.responses.MusicBrainzResponse;
import com.plugs.music.model.responses.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
public class MusicBrainzClient extends BasicClient {

    private static final String RESOURCE_URL = "/ws/2/artist/%s?&fmt=json&inc=url-rels+release-groups";

    public MusicBrainzClient(WebClient webClient) {
        super(webClient);
    }

    public Mono<Response> getResourceInfo(Mono<UUID> mbid) {
        log.debug("Getting the artist data by mbid ");
        return mbid.flatMap(uuid -> get(String.format(RESOURCE_URL, uuid), MusicBrainzResponse.class));
    }

}
