package com.tomasdutkus.movierental.exception;

public class MovieAlreadyExistException extends RuntimeException {


    private static final long serialVersionUID = -529181854207650801L;

    public MovieAlreadyExistException(String fileName) {
        super(String.format("Movie with name : %s already exist", fileName));
    }
}