package com.tomasdutkus.movierental.dao;

import com.tomasdutkus.movierental.model.Movie;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieInventoryRepository extends PagingAndSortingRepository<Movie, Long> {

    public Optional<Movie> findByName(String fileName);
}

