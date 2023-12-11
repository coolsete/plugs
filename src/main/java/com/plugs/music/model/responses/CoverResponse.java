package com.plugs.music.model.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.plugs.music.deserializers.CoverDeserializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@JsonDeserialize(using = CoverDeserializer.class)
public class CoverResponse extends Response {
    private final List<String> coverImageSrcList;
}
