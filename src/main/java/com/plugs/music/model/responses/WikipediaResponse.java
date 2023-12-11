package com.plugs.music.model.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.plugs.music.deserializers.WikipediaResponseDeserializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@JsonDeserialize(using = WikipediaResponseDeserializer.class)
public class WikipediaResponse extends Response {
    private final String description;
}
