package com.webxemphim.demo.repository;

import com.webxemphim.demo.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieInterface extends JpaRepository<Movie, Integer> {
    List<Movie> findAllByOrderByRatingMeanDesc();
    
}
