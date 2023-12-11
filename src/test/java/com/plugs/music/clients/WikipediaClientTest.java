package com.plugs.music.clients;

import com.plugs.music.configurations.IntegrationTestConfiguration;
import com.plugs.music.model.responses.WikipediaResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {IntegrationTestConfiguration.class})
public class WikipediaClientTest {
    @Autowired
    private WikipediaClient wikipediaClient;
    @Test
    public void musicWikiDataClientShouldProvideArtistsInformationByExistingId() {
        StepVerifier
                .create(wikipediaClient.getWikipediaInfo(Mono.just("John Williams")))
                .consumeNextWith(externalApiResponse -> {
                    assertTrue(externalApiResponse instanceof WikipediaResponse);
                    assertFalse(((WikipediaResponse)externalApiResponse).getDescription().isEmpty());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void musicWikiDataClientShouldReturnErrorByNonExistingId(){
        StepVerifier
                .create(wikipediaClient.getWikipediaInfo(Mono.just("Q143128d5")))
                .expectError(ClassCastException.class)
                .verify();
    }
}
