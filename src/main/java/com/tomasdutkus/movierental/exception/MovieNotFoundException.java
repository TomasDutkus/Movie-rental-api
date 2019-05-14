package com.tomasdutkus.movierental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such movie")
public class MovieNotFoundException extends RuntimeException {


    private static final long serialVersionUID = 1124620077757615293L;

    public MovieNotFoundException(String movieName) {
        super(String.format("Movie with name : %s does not exist", movieName));
    }

    public MovieNotFoundException(Long movieId) {
        super(String.format("Movie with id : %d does not exist", movieId));
    }
}
