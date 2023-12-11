package com.plugs.music.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plugs.music.configurations.IntegrationTestConfiguration;
import com.plugs.music.model.domain.Artist;
import com.plugs.music.services.MusicService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(ArtistsController.class)
@Import(IntegrationTestConfiguration.class)
public class ArtistsControllerTest {

    @Autowired
    protected MusicService musicService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    public void artistsControllerShouldProvidesInformationAboutArtistMadonna() {
        var result = WebTestClient
                .bindToController(new ArtistsController(musicService))
                .build()
                .get()
                .uri("/api/v1/artists/79239441-bfd5-4981-a70c-55c3f15c1287")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Artist.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getName(), "Madonna");
        Assertions.assertEquals(result.getMbid(), "79239441-bfd5-4981-a70c-55c3f15c1287");
        Assertions.assertNotNull(result.getDescription());
        Assertions.assertFalse(result.getAlbums().isEmpty());
    }

    @Test
    @SneakyThrows
    public void artistsControllerShouldProvidesInformationAboutArtistMetallica() {
        var result = WebTestClient
                .bindToController(new ArtistsController(musicService))
                .build()
                .get()
                .uri("/api/v1/artists/65f4f0c5-ef9e-490c-aee3-909e7ae6b2ab")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Artist.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getName(), "Metallica");
        Assertions.assertEquals(result.getMbid(), "65f4f0c5-ef9e-490c-aee3-909e7ae6b2ab");
        Assertions.assertNotNull(result.getDescription());
        Assertions.assertFalse(result.getAlbums().isEmpty());
    }

    @Test
    @SneakyThrows
    public void artistsControllerShouldProvidesInformationAboutArtistJohnWilliams() {
        var result = WebTestClient
                .bindToController(new ArtistsController(musicService))
                .build()
                .get()
                .uri("/api/v1/artists/53b106e7-0cc6-42cc-ac95-ed8d30a3a98e")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Artist.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getName(), "John Williams");
        Assertions.assertEquals(result.getMbid(), "53b106e7-0cc6-42cc-ac95-ed8d30a3a98e");
        Assertions.assertNotNull(result.getDescription());
        Assertions.assertFalse(result.getAlbums().isEmpty());
    }

    @Test
    @SneakyThrows
    public void artistsControllerShouldReturnErrorForNonExistingArtist() {
        WebTestClient
                .bindToController(new ArtistsController(musicService))
                .build()
                .get()
                .uri("/api/v1/artists/53b106e7-0cc6")
                .exchange()
                .expectStatus().is4xxClientError();
    }

}
