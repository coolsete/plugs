package com.plugs.music.model.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.plugs.music.deserializers.MusicBrainzResponseDeserializer;
import com.plugs.music.model.domain.ReleaseGroup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@JsonDeserialize(using = MusicBrainzResponseDeserializer.class)
public class MusicBrainzResponse extends Response {
    private final String wikiDataResourceId;
    private final String name;
    private final List<ReleaseGroup> releaseGroups;
}
