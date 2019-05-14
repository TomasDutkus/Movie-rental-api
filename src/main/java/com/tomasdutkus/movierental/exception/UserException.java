package com.tomasdutkus.movierental.exception;

import org.springframework.http.HttpStatus;

public class UserException extends Exception {


    private static final long serialVersionUID = -1497076689516457931L;

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(String message, HttpStatus statusCode) {
        super(message);
        this.status = statusCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
