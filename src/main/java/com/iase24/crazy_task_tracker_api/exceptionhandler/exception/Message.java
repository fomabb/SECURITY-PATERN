package com.iase24.crazy_task_tracker_api.exceptionhandler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@Getter
public enum Message {
    ADDRESS_OUT_OF_DELIVERY_ZONE("Address out of delivery zone", NOT_ACCEPTABLE);

    final String description;
    final HttpStatus httpStatus;

    Message(String description, HttpStatus httpStatus) {
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
