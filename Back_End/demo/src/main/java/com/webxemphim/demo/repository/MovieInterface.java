package com.webxemphim.demo.repository;

import com.webxemphim.demo.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieInterface extends JpaRepository<Movie, Integer> {
    List<Movie> findAllByOrderByRatingMeanDesc();
    boolean existsByMovieName(String movieName);
    Optional<Movie> findFirstByMovieName(String movieName);

}
