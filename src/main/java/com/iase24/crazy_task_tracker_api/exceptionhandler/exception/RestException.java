package com.iase24.crazy_task_tracker_api.exceptionhandler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RestException extends RuntimeException {
    private final HttpStatus httpStatus;

    public RestException(Message exception) {
        super(exception.getDescription());
        this.httpStatus = exception.getHttpStatus();
    }
}
