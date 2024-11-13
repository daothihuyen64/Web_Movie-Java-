package com.webxemphim.demo.service.imp;

import java.util.List;

import com.webxemphim.demo.dto.FavoriteMovieDTO;
import com.webxemphim.demo.dto.SimpleMovieDTO;
import com.webxemphim.demo.dto.SubscriptionDTO;
import com.webxemphim.demo.dto.TransactionHistoryDTO;
import com.webxemphim.demo.dto.UpdateUserDTO;
import com.webxemphim.demo.dto.UserDTO;
import com.webxemphim.demo.entity.User;
import com.webxemphim.demo.payload.ResponseData;

public interface UserServiceImp {
    List<UserDTO> getAllUser();
    UserDTO getUser(int id);
    ResponseData updateUser(int userId, UpdateUserDTO updateUserDTO);
    boolean addFavoriteMovie(FavoriteMovieDTO favoriteMovieDTO);
    List<SimpleMovieDTO> getFavoriteMovies(int userId);
    void deleteMovieFavourite(int userId, int movieId);
    ResponseData registerSubscription(SubscriptionDTO subscriptionDTO);
    List<TransactionHistoryDTO> getTransactionHistory(int userId);
    List<SimpleMovieDTO> searchMoviesByName(String movieName);
    List<SimpleMovieDTO> searchMoviesByActorName(String actorName);
}
