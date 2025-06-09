package com.example.tracking.exception;

import com.example.tracking.model.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ResponseEntityException extends RuntimeException {
    @Getter
    private final ErrorResponse responseEntity;
    private final HttpStatusCode status;

    public ResponseEntityException(ErrorResponse responseEntity) {
        super(responseEntity.getMessage().toString());
        this.responseEntity = responseEntity;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
