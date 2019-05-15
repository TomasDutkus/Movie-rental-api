package com.tomasdutkus.movierental.service.impl;

import com.tomasdutkus.movierental.dao.MovieInventoryRepository;
import com.tomasdutkus.movierental.exception.MovieAlreadyExistException;
import com.tomasdutkus.movierental.exception.MovieNotFoundException;
import com.tomasdutkus.movierental.model.Movie;
import com.tomasdutkus.movierental.service.MovieInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MovieInventoryServiceImpl implements MovieInventoryService {

    @Autowired
    private MovieInventoryRepository movieInventoryRepository;

    @Override
    public Movie addMovieToInventory(Movie movie) {
        try {
            getMovieByName(movie.getName());
        } catch (MovieNotFoundException rnfe) {
            return this.movieInventoryRepository.save(movie);
        }

        throw new MovieAlreadyExistException(movie.getName());
    }

    @Override
    public Page<Movie> getAllMovies(PageRequest pageRequest) {
        return this.movieInventoryRepository.findAll(pageRequest);
    }

    @Override
    public Movie getMovieById(Long id) {
        return this.movieInventoryRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }

    @Override
    public Movie getMovieByName(String name) {
        return this.movieInventoryRepository.findByName(name).orElseThrow(() -> new MovieNotFoundException(name));
    }

    @Override
    public Boolean deleteMovieFromInventory(Long id) {
        this.movieInventoryRepository.findById(id).map(film -> {
            this.movieInventoryRepository.delete(film);
            return true;
        }).orElseThrow(() -> new MovieNotFoundException(id));

        return false;
    }
}
