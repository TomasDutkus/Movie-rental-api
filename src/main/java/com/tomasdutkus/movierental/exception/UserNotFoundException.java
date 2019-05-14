package com.tomasdutkus.movierental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such user")
public class UserNotFoundException extends RuntimeException {


    private static final long serialVersionUID = -2374368016753442226L;

    public UserNotFoundException(String emailId) {
        super(String.format("User with Email id : %s does not exist", emailId));
    }

    public UserNotFoundException(Long userId) {
        super(String.format("User with id : %d does not exist", userId));
    }
}
