package com.plugs.music.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.plugs.music.model.responses.WikipediaResponse;

import java.io.IOException;

public class WikipediaResponseDeserializer extends JsonDeserializer<WikipediaResponse> {
    @Override
    public WikipediaResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return new WikipediaResponse(getDescription(jsonParser.getCodec().readTree(jsonParser)));
    }

    private String getDescription(JsonNode rootNode) {
        String descriptionValue = rootNode.findPath("extract").textValue();
        if (descriptionValue != null) {
            return descriptionValue;
        }
        throw new ClassCastException("Wikipedia response has no 'extract' path");
    }
}
