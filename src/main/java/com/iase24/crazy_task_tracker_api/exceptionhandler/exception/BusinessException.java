package com.iase24.crazy_task_tracker_api.exceptionhandler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private String message;
}