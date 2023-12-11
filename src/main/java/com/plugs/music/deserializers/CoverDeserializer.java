package com.plugs.music.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.plugs.music.model.responses.CoverResponse;

import java.io.IOException;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class CoverDeserializer extends JsonDeserializer<CoverResponse> {
    @Override
    public CoverResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return new CoverResponse(getImageSource(jsonParser.getCodec().readTree(jsonParser)));
    }

    private List<String> getImageSource(JsonNode rootNode) {
        var images = (ArrayNode) rootNode.findPath("images");
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(images.elements(), Spliterator.ORDERED), false).map(
                image -> image.findPath("image").textValue()
        ).toList();
    }
}
