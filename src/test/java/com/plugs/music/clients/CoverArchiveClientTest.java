package com.plugs.music.clients;

import com.plugs.music.configurations.IntegrationTestConfiguration;
import com.plugs.music.model.domain.ReleaseGroup;
import com.plugs.music.model.responses.CoverResponse;
import com.plugs.music.model.responses.MusicBrainzResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {IntegrationTestConfiguration.class})
public class CoverArchiveClientTest {

    @Autowired
    private CoverArchiveClient coverArchiveClient;

    @Autowired
    private MusicBrainzClient musicBrainzClient;

    @Test
    public void musicBrainzClientShouldProvideArtistsInformationByExistingId() {
        var releases = new ArrayList<String>();
        StepVerifier
                .create(musicBrainzClient.getResourceInfo(Mono.just(UUID.fromString("53b106e7-0cc6-42cc-ac95-ed8d30a3a98e"))))
                .consumeNextWith(externalApiResponse -> {
                    assertTrue(externalApiResponse instanceof MusicBrainzResponse);
                    assertEquals("John Williams", ((MusicBrainzResponse)externalApiResponse).getName());
                    releases.addAll(((MusicBrainzResponse) externalApiResponse).getReleaseGroups().stream().map(ReleaseGroup::getUid).toList());
                    assertFalse(((MusicBrainzResponse) externalApiResponse).getReleaseGroups().isEmpty());
                })
                .expectComplete()
                .verify();
            StepVerifier
                    .create(coverArchiveClient.getImages(Mono.just(releases.get(0))))
                    .consumeNextWith(externalApiResponse -> {
                        assertTrue(externalApiResponse instanceof CoverResponse);
                        assertFalse(((CoverResponse)externalApiResponse).getCoverImageSrcList().isEmpty());
                    })
                    .expectComplete()
                    .verify();
    }

    @Test
    public void musicBrainzClientShouldReturnErrorByNonExistingId(){
        StepVerifier
                .create(coverArchiveClient.getImages(Mono.just("3bd6510e-9775-11ee-b9d1-0242ac120002")))
                .expectError(UnsupportedMediaTypeException.class)
                .verify();
    }


}
