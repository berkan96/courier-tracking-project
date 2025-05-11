package com.demo.courier.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CourierRuntimeException extends RuntimeException {

    private int code;
    private HttpStatus status;

    public CourierRuntimeException(int code, String message) {
        this(BAD_REQUEST, code, message);
    }

    public CourierRuntimeException(String message) {
        super(message);
    }

    public CourierRuntimeException(HttpStatus status, int code, String message) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
