package com.plugs.music.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.plugs.music.model.responses.ErrorResponse;

import java.io.IOException;

public class ErrorDeserializer extends JsonDeserializer<ErrorResponse> {
    @Override
    public ErrorResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        return new ErrorResponse(rootNode.findPath("error").textValue());
    }
}
