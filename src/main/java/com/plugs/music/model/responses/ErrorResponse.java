package com.plugs.music.model.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.plugs.music.deserializers.ErrorDeserializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonDeserialize(using = ErrorDeserializer.class)
public class ErrorResponse extends Response {
    private final String message;
}
