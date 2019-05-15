package com.tomasdutkus.movierental.service;

import com.tomasdutkus.movierental.model.Rental;

import java.util.List;

public interface RentalService {

    public Double rentMovies(Rental rental);

    public List<Rental> getAllRentalsByUserId(Long userId);

    public Rental getRentalById(Long id);

    public Double returnMovies(Rental rental);
}
