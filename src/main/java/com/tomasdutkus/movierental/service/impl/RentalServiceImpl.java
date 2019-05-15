package com.tomasdutkus.movierental.service.impl;

import com.tomasdutkus.movierental.dao.MovieInventoryRepository;
import com.tomasdutkus.movierental.dao.RentalRepository;
import com.tomasdutkus.movierental.dao.UserRepository;
import com.tomasdutkus.movierental.exception.MovieNotFoundException;
import com.tomasdutkus.movierental.exception.RentalNotFoundException;
import com.tomasdutkus.movierental.exception.UserNotFoundException;
import com.tomasdutkus.movierental.model.Movie;
import com.tomasdutkus.movierental.model.Rental;
import com.tomasdutkus.movierental.model.RentalItem;
import com.tomasdutkus.movierental.model.User;
import com.tomasdutkus.movierental.service.RentalService;
import com.tomasdutkus.movierental.service.util.RentalPricingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    MovieInventoryRepository movieInventoryRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Rental getRentalById(Long id) {
        return this.rentalRepository.findById(id).orElseThrow(() -> new RentalNotFoundException(id));
    }

    @Override
    public Double rentMovies(Rental rental) {
        List<RentalItem> rentalItems = rental.getRentalItems();

        // Validate and get user details
        User user = this.userRepository.findById(rental.getUserId())
                .orElseThrow(() -> new UserNotFoundException(rental.getUserId()));

        // Calculate rental price and store in rental object
        Double rentalTotalPrice = rentalItems.stream().mapToDouble(this::getRentalPrice).sum();
        rental.setRenteddate(LocalDateTime.now());
        rental.setPrice(rentalTotalPrice);
        this.rentalRepository.save(rental);

        return rentalTotalPrice;
    }

    @Override
    public Double returnMovies(Rental rental) {
        // Validate rental details with rental id
        this.rentalRepository.findById(rental.getId())
                .orElseThrow(() -> new RentalNotFoundException(rental.getId()));

        // Validate user details
        this.userRepository.findById(rental.getUserId())
                .orElseThrow(() -> new UserNotFoundException(rental.getUserId()));

        List<RentalItem> rentalItems = rental.getRentalItems();

        // Calculate surcharge price and store in rental object
        Double surcharge = rentalItems.stream().mapToDouble(this::getSurchargePrice).sum();
        rental.setOrderReturned(true);
        rental.setSurcharge(surcharge);
        this.rentalRepository.save(rental);

        return surcharge;
    }

    @Override
    public List<Rental> getAllRentalsByUserId(Long userId) {
        return this.rentalRepository.findByUserId(userId);
    }

    private Double getRentalPrice(RentalItem rentalItem) {
        // Validate film details
        Movie movie = this.movieInventoryRepository.findById(rentalItem.getMovieId())
                .orElseThrow(() -> new MovieNotFoundException(rentalItem.getMovieId()));
        return RentalPricingUtil.calculateRentalPricing(rentalItem, movie.getMovieType());
    }

    private Double getSurchargePrice(RentalItem rentalItem) {
        // Validate film details
        Movie movie = this.movieInventoryRepository.findById(rentalItem.getMovieId())
                .orElseThrow(() -> new MovieNotFoundException(rentalItem.getMovieId()));
        return RentalPricingUtil.calculateReturnSurcharge(rentalItem, movie.getMovieType());
    }
}
