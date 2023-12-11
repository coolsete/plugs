package com.plugs.music.model.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Artist {
    private String mbid;
    private String name;
    private String description;
    private List<Album> albums;
}
