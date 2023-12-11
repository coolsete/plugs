package com.plugs.music.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.plugs.music.model.responses.WikiDataResponse;

import java.io.IOException;

public class WikiDataResponseDeserializer extends JsonDeserializer<WikiDataResponse> {
    @Override
    public WikiDataResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return new WikiDataResponse(getSiteLinkTitle(jsonParser.getCodec().readTree(jsonParser)));
    }

    private String getSiteLinkTitle(JsonNode rootNode) {
        return rootNode.findPath("sitelinks").path("enwiki").path("title").textValue();
    }
}
