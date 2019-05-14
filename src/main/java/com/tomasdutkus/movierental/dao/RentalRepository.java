package com.tomasdutkus.movierental.dao;

import com.tomasdutkus.movierental.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}
