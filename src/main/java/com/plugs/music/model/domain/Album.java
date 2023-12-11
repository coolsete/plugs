package com.plugs.music.model.domain;

import java.util.List;

public record Album(String id, String title, List<String> coverImages) {
}
