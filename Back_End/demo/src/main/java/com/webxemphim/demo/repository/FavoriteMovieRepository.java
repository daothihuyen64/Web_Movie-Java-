package com.webxemphim.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webxemphim.demo.entity.Favorite_Movie;

public interface FavoriteMovieRepository extends JpaRepository<Favorite_Movie, Integer>{
    Favorite_Movie findByUserIdAndMovieId(int userId, int movieId);
    List<Favorite_Movie> findByUserId(int userId);
}