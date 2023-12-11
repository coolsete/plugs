package com.plugs.music.services.impl;

import com.plugs.music.clients.CoverArchiveClient;
import com.plugs.music.clients.MusicBrainzClient;
import com.plugs.music.clients.WikiDataClient;
import com.plugs.music.clients.WikipediaClient;
import com.plugs.music.model.domain.Album;
import com.plugs.music.model.domain.Artist;
import com.plugs.music.model.responses.*;
import com.plugs.music.services.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class MusicServiceImpl implements MusicService {
    private final MusicBrainzClient musicBrainzClient;
    private final WikiDataClient wikiDataClient;
    private final WikipediaClient wikipediaClient;
    private final CoverArchiveClient coverArchiveClient;

    @Override
    public Mono<Artist> getArtistByMbid(UUID mbid) {
        log.info("Getting an artist with mbid '" + mbid + "' info");
        return Mono.defer(() -> Mono.just(new Artist())
                .flatMap(artist -> {
                    artist.setMbid(mbid.toString());
                    return musicBrainzClient.getResourceInfo(Mono.just(mbid))
                            .flatMap(musicBrainzData -> {
                                artist.setName(((MusicBrainzResponse) musicBrainzData).getName());
                                return getAlbumCovers((MusicBrainzResponse) musicBrainzData, artist)
                                        .flatMap(artist1 -> wikiDataClient.getEntityInfo(Mono.just(((MusicBrainzResponse) musicBrainzData).getWikiDataResourceId()))
                                                .flatMap(wikidata -> wikipediaClient.getWikipediaInfo(Mono.just(((WikiDataResponse) wikidata).getTitle()))
                                                        .flatMap(wikipedia -> {
                                                            artist1.setDescription(((WikipediaResponse) wikipedia).getDescription());
                                                            return Mono.just(artist1);
                                                        })
                                                ).doOnSuccess(Mono::just));

                            });
                }));
    }


    private Mono<Artist> getAlbumCovers(MusicBrainzResponse musicBrainzResponse, Artist artist) {
        log.debug("Requesting covers for artist " + musicBrainzResponse.getName());
        var requests = Flux.fromIterable(musicBrainzResponse.getReleaseGroups())
                .flatMap(release -> coverArchiveClient.getImages(Mono.just(release.getUid()))
                        .map(response -> new Album(release.getUid(), release.getTitle(),
                                ((CoverResponse) response).getCoverImageSrcList())));
        return requests.collectList().timeout(Duration.ofSeconds(20)).map(albums -> {
            artist.setAlbums(albums);
            return artist;
        });
    }
}
