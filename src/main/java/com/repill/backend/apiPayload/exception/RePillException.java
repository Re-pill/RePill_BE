package com.repill.backend.apiPayload.exception;

import org.springframework.http.HttpStatus;

public class RePillException extends RuntimeException {

    public final HttpStatus status;

    public RePillException(final HttpStatus status, final String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
