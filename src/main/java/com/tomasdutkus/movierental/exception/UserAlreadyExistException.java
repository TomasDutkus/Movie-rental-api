package com.tomasdutkus.movierental.exception;

public class UserAlreadyExistException extends RuntimeException {


    private static final long serialVersionUID = 2516051140149658894L;

    public UserAlreadyExistException(String userName) {
        super(String.format("User with name : %s already exist", userName));
    }
}
