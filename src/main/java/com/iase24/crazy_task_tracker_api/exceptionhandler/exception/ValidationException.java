package com.iase24.crazy_task_tracker_api.exceptionhandler.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}