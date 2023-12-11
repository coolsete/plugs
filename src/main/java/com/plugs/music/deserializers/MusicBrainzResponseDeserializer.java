package com.plugs.music.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.plugs.music.model.responses.MusicBrainzResponse;
import com.plugs.music.model.domain.ReleaseGroup;

import java.util.*;
import java.util.stream.StreamSupport;
import java.io.IOException;

public class MusicBrainzResponseDeserializer extends JsonDeserializer<MusicBrainzResponse> {
    @Override
    public MusicBrainzResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode wikiDataNode = getWikipediaNode(rootNode);
        return new MusicBrainzResponse(getWikiDataResourceId(wikiDataNode), getName(rootNode),
                getReleaseGroupList(rootNode));
    }

    private JsonNode getWikipediaNode(JsonNode rootNode) {
        ArrayNode relations = (ArrayNode) rootNode.findPath("relations");
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(relations.elements(), Spliterator.ORDERED), false)
                .filter(node -> node.path("type").textValue().equals("wikidata"))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("No relation of type 'wikidata'."));
    }

    private String getName(JsonNode rootNode) {
        return rootNode.get("name").textValue();
    }

    private String getWikiDataResourceId(JsonNode wikipediaNode) {
        return wikipediaNode.path("url").path("resource").textValue().replace("https://www.wikidata.org/wiki/", "");
    }

    private List<ReleaseGroup> getReleaseGroupList(JsonNode rootNode) {
        ArrayNode releaseGroups = (ArrayNode) rootNode.findPath("release-groups");
        var result = new ArrayList<ReleaseGroup>();
        StreamSupport.stream(Spliterators.spliteratorUnknownSize(releaseGroups.elements(), Spliterator.ORDERED), false)
                .forEach(jsonNode -> result.add(
                        new ReleaseGroup(jsonNode.path("id").textValue(),
                        jsonNode.path("first-release-date").textValue(),
                        jsonNode.path("title").textValue(),
                        jsonNode.path("primary-type").textValue())));
        return result;
    }
}
