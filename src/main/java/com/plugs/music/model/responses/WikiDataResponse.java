package com.plugs.music.model.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.plugs.music.deserializers.WikiDataResponseDeserializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@JsonDeserialize(using = WikiDataResponseDeserializer.class)
public class WikiDataResponse extends Response {
    private final String title;
}
