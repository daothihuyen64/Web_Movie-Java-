package com.webxemphim.demo.service.imp;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.webxemphim.demo.dto.FavoriteMovieDTO;
import com.webxemphim.demo.dto.MovieDTO;
import com.webxemphim.demo.dto.SubscriptionDTO;
import com.webxemphim.demo.dto.TransactionHistoryDTO;
import com.webxemphim.demo.dto.UserDTO;

public interface UserServiceImp {
    UserDTO getUser(int id);
    boolean updateUser(int userId, UserDTO userDTO);
    boolean addFavoriteMovie(FavoriteMovieDTO favoriteMovieDTO);
    List<MovieDTO> getFavoriteMovies(int userId);
    void deleteMovieFavourite(int userId, int movieId);
    String registerSubscription(SubscriptionDTO subscriptionDTO);
    List<TransactionHistoryDTO> getTransactionHistory(int userId);
    List<MovieDTO> searchMoviesByName(String movieName);
    List<MovieDTO> searchMoviesByActorName(String actorName);
}
