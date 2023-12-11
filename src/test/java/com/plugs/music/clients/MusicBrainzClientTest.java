package com.plugs.music.clients;

import com.plugs.music.configurations.IntegrationTestConfiguration;
import com.plugs.music.exceptions.MusicClientException;
import com.plugs.music.model.responses.MusicBrainzResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {IntegrationTestConfiguration.class})
public class MusicBrainzClientTest {
    @Autowired
    private MusicBrainzClient musicBrainzClient;

    @Test
    public void musicBrainzClientShouldProvideArtistsInformationByExistingId() {
        StepVerifier
                .create(musicBrainzClient.getResourceInfo(Mono.just(UUID.fromString("53b106e7-0cc6-42cc-ac95-ed8d30a3a98e"))))
                .consumeNextWith(externalApiResponse -> {
                    assertTrue(externalApiResponse instanceof MusicBrainzResponse);
                    assertEquals("John Williams", ((MusicBrainzResponse)externalApiResponse).getName());
                    assertFalse(((MusicBrainzResponse) externalApiResponse).getReleaseGroups().isEmpty());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void musicBrainzClientShouldReturnErrorByNonExistingId(){
        StepVerifier
                .create(musicBrainzClient.getResourceInfo(Mono.just(UUID.fromString("3bd6510e-9775-11ee-b9d1-0242ac120002"))))
                .expectError(MusicClientException.class)
                .verify();
    }
}
