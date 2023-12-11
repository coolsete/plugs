package com.plugs.music.model.responses;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SuccessResponse extends Response {
    private final String body;
}
