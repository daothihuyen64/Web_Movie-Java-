package com.webxemphim.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.webxemphim.demo.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>{

    @Query("SELECT m FROM movie m WHERE LOWER(m.movieName) LIKE LOWER(CONCAT('%', :movieName, '%'))")
    List<Movie> searchMoviesByName(@Param("movieName") String movieName);

    @Query("SELECT m FROM movie m JOIN m.movie_actorList ma JOIN ma.actor a " +
           "WHERE LOWER(a.nameActor) LIKE LOWER(CONCAT('%', :actorName, '%'))")
    List<Movie> searchMoviesByActorName(@Param("actorName") String actorName);
}
