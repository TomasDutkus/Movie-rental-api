package com.tomasdutkus.movierental.dao;

import com.tomasdutkus.movierental.model.Movie;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieInventoryRepository extends PagingAndSortingRepository<Movie, Long> {
}

