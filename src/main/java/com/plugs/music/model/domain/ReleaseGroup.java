package com.plugs.music.model.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class ReleaseGroup {
    private String uid;
    private String year;
    private String title;
    private String type;
    private List<String> coverImage;

    public ReleaseGroup(String uid, String year, String title, String type) {
        this.uid = uid;
        this.year = year;
        this.title = title;
        this.type = type;
    }
}



