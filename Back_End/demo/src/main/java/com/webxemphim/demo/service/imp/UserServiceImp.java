package com.webxemphim.demo.service.imp;

import java.util.List;

import com.webxemphim.demo.dto.FavoriteMovieDTO;
import com.webxemphim.demo.dto.SimpleMovieDTO;
import com.webxemphim.demo.dto.SubscriptionDTO;
import com.webxemphim.demo.dto.TransactionHistoryDTO;
import com.webxemphim.demo.dto.UserDTO;
import com.webxemphim.demo.payload.ResponseData;

public interface UserServiceImp {
    UserDTO getUser(int id);
    boolean updateUser(int userId, UserDTO userDTO);
    boolean addFavoriteMovie(FavoriteMovieDTO favoriteMovieDTO);
    List<SimpleMovieDTO> getFavoriteMovies(int userId);
    void deleteMovieFavourite(int userId, int movieId);
    ResponseData registerSubscription(SubscriptionDTO subscriptionDTO);
    List<TransactionHistoryDTO> getTransactionHistory(int userId);
    List<SimpleMovieDTO> searchMoviesByName(String movieName);
    List<SimpleMovieDTO> searchMoviesByActorName(String actorName);
}
