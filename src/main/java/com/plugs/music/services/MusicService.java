package com.plugs.music.services;

import com.plugs.music.model.domain.Artist;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MusicService {
    Mono<Artist> getArtistByMbid(UUID mbid);
}
