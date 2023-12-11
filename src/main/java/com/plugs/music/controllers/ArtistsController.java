package com.plugs.music.controllers;

import com.plugs.music.model.domain.Artist;
import com.plugs.music.services.MusicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ArtistsController {
    private final MusicService musicService;

    public ArtistsController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/artists/{mbid}")
    public Mono<ResponseEntity<Artist>> getArtist(@PathVariable UUID mbid) {
        log.debug("Request get artist received with '" + mbid + "'");

        return musicService.getArtistByMbid(mbid)
                .flatMap(artist -> Mono.just(new ResponseEntity<>(artist, HttpStatus.OK)))
                .doOnSuccess(artistResponseEntity -> {
                    log.debug("Request succeed with '" + mbid + "'");
                })
                .doOnError(artistResponseError -> {
                    log.error("Request error with '" + mbid + "' " + artistResponseError.getMessage());
                });
    }
}
