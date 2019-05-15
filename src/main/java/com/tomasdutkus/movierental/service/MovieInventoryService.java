package com.tomasdutkus.movierental.service;

import com.tomasdutkus.movierental.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface MovieInventoryService {

    public Movie addMovieToInventory(Movie movie);

    public Movie getMovieByName(String name);

    public Page<Movie> getAllMovies(PageRequest pageRequest);

    public Boolean deleteMovieFromInventory(Long id);

    public Movie getMovieById(Long id);

}
