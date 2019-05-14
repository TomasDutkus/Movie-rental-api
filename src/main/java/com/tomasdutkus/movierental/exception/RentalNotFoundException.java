package com.tomasdutkus.movierental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such rental")
public class RentalNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6704530421873863497L;

    public RentalNotFoundException(Long rentalId) {
        super(String.format("Rental with id : %d does not exist", rentalId));
    }
}