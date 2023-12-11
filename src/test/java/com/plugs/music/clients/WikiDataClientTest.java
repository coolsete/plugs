package com.plugs.music.clients;

import com.plugs.music.configurations.IntegrationTestConfiguration;
import com.plugs.music.model.responses.WikiDataResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {IntegrationTestConfiguration.class})
public class WikiDataClientTest {

    @Autowired
    private WikiDataClient wikiDataClient;
    @Test
    public void musicWikiDataClientShouldProvideArtistsInformationByExistingId() {
        StepVerifier
                .create(wikiDataClient.getEntityInfo(Mono.just("Q131285")))
                .consumeNextWith(externalApiResponse -> {
                    assertTrue(externalApiResponse instanceof WikiDataResponse);
                    assertEquals("John Williams", ((WikiDataResponse)externalApiResponse).getTitle());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void musicWikiDataClientShouldReturnErrorByNonExistingId(){
        StepVerifier
                .create(wikiDataClient.getEntityInfo(Mono.just("Q143128d5")))
                .expectError(UnsupportedMediaTypeException.class)
                .verify();
    }
}
