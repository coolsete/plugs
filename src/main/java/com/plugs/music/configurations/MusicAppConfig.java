package com.plugs.music.configurations;

import com.plugs.music.clients.CoverArchiveClient;
import com.plugs.music.clients.MusicBrainzClient;
import com.plugs.music.clients.WikiDataClient;
import com.plugs.music.clients.WikipediaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


@Configuration
public class MusicAppConfig {

    private static int MAX_INIT_MEMORY_SIZE = 16 * 1024 * 1024;

    @Value("${music.musicBrainzUrl:http://musicbrainz.org}")
    private String musicBrainzUrl;

    @Value("${music.wikidataUrl:https://www.wikidata.org}")
    private String wikidataUrl;

    @Value("${music.wikipediaUrl:https://en.wikipedia.org}")
    private String wikipediaUrl;

    @Value("${music.coversUrl:https://coverartarchive.org}")
    private String coversUrl;

    @Bean
    public MusicBrainzClient musicBrainzClient(WebClient.Builder builder) {
        return new MusicBrainzClient(builder.baseUrl(musicBrainzUrl).build());
    }

    @Bean
    public WikiDataClient wikidataClient(WebClient.Builder builder) {
        return new WikiDataClient(builder.exchangeStrategies(ExchangeStrategies.builder().codecs(
                        clientCodecConfigurer ->
                                clientCodecConfigurer.defaultCodecs().maxInMemorySize(MAX_INIT_MEMORY_SIZE))
                .build()).baseUrl(wikidataUrl).build());
    }

    @Bean
    public WikipediaClient wikipediaClient(WebClient.Builder builder) {
        return new WikipediaClient(builder.baseUrl(wikipediaUrl).build());
    }
    @Bean
    public CoverArchiveClient coverArchiveClient(WebClient.Builder builder) {
        return new CoverArchiveClient(builder.clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                .followRedirect(true))).baseUrl(coversUrl).build());
    }

}
