package com.iase24.crazy_task_tracker_api.exceptionhandler;

import com.iase24.crazy_task_tracker_api.dto.exception.CommonExceptionResponse;
import com.iase24.crazy_task_tracker_api.exceptionhandler.exception.BusinessException;
import com.iase24.crazy_task_tracker_api.exceptionhandler.exception.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CommonExceptionResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildResponseBody(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<CommonExceptionResponse> handleValidationException(ValidationException e) {
        return ResponseEntity.unprocessableEntity()
                .body(buildResponseBody(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonExceptionResponse> handleBusinessException(BusinessException e) {
        return ResponseEntity.unprocessableEntity()
                .body(buildResponseBody(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> handleRuntimeException() {
        return ResponseEntity.internalServerError().build();
    }

    private CommonExceptionResponse buildResponseBody(String message, String exceptionClass) {
        return CommonExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .exceptionClass(exceptionClass)
                .message(message)
                .build();
    }
}