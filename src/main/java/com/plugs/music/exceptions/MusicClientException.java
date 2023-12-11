package com.plugs.music.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class MusicClientException extends RuntimeException {
    private final HttpStatusCode responseStatus;
    private final String errorMessage;

    public MusicClientException(HttpStatusCode responseStatus, String errorMessage) {
        super(errorMessage);
        this.responseStatus = responseStatus;
        this.errorMessage = errorMessage;
    }
}
