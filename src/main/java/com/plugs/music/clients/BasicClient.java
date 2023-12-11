package com.plugs.music.clients;

import com.plugs.music.exceptions.MusicClientException;
import com.plugs.music.model.responses.ErrorResponse;
import com.plugs.music.model.responses.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
public class BasicClient {
    protected final WebClient webClient;

    protected Mono<Response> get(String url, Class<? extends Response> clazz) {
        return webClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> deserializeResponse(clientResponse, clazz));
    }

    protected Mono<Response> deserializeResponse(ClientResponse clientResponse, Class<? extends Response> clazz) {
        var responseStatus = clientResponse.statusCode();

        if (responseStatus == HttpStatus.OK) {
            return clientResponse.bodyToMono(clazz);
        } else
            return clientResponse.bodyToMono(ErrorResponse.class)
                    .flatMap(errorMessage -> Mono.error(new MusicClientException(responseStatus, errorMessage.getMessage())));
    }
}
