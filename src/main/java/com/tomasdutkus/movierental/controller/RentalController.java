package com.tomasdutkus.movierental.controller;

import com.tomasdutkus.movierental.model.Rental;
import com.tomasdutkus.movierental.service.RentalService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class RentalController {

    private final Logger logger = Logger.getLogger(RentalController.class);

    @Autowired
    private RentalService rentalService;

    @PostMapping(value = "/rentals", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, String>> rentFilms(@RequestBody Rental rental) {
        this.logger.info("Getting request for persisting rental object : " + rental.toString());

        Double rentedprice = this.rentalService.rentMovies(rental);

        Map<String, String> responseEntity = new LinkedHashMap<>();
        responseEntity.put("rentedprice", rentedprice.toString());

        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    @PostMapping(value = "/rentals/return", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, String>> returnFilms(@RequestBody Rental rental) {
        this.logger.info("Getting request for returning rental object with : " + rental.toString());

        Double surcharge = this.rentalService.returnMovies(rental);

        Map<String, String> responseEntity = new LinkedHashMap<>();
        responseEntity.put("surcharge", surcharge.toString());

        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

  /*  @GetMapping(value = "/rentals/{userId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Rental>> getAllRentalsByUserId(@PathVariable long userId) {
        this.logger.info("Getting request for getting rental object by user id : " + userId);
        return new ResponseEntity<>(this.rentalService.getAllRentalsByUserId(userId), HttpStatus.OK);
    } */

    @GetMapping(value = "/rentals/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Rental> getRentalById(@PathVariable long id) {
        this.logger.info("Getting request for getting rental object by id : " + id);

        Rental rental = this.rentalService.getRentalById(id);

        if (rental != null) {
            return ResponseEntity.status(HttpStatus.OK).body(rental);
        }

        this.logger.info("Rental not found for the id : " + id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
