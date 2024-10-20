package com.webxemphim.demo.service;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webxemphim.demo.dto.FavoriteMovieDTO;
import com.webxemphim.demo.dto.MovieDTO;
import com.webxemphim.demo.dto.SubscriptionDTO;
import com.webxemphim.demo.dto.TransactionHistoryDTO;
import com.webxemphim.demo.dto.UserDTO;
import com.webxemphim.demo.entity.Favorite_Movie;
import com.webxemphim.demo.entity.Movie;
import com.webxemphim.demo.entity.Packages;
import com.webxemphim.demo.entity.Transaction;
import com.webxemphim.demo.entity.User;
import com.webxemphim.demo.repository.FavoriteMovieRepository;
import com.webxemphim.demo.repository.MovieRepository;
import com.webxemphim.demo.repository.PackagesRepository;
import com.webxemphim.demo.repository.TransactionRepository;
import com.webxemphim.demo.repository.UserRepository;
import com.webxemphim.demo.service.imp.UserServiceImp;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserServiceImp{

    @Autowired
    UserRepository userRepository;

    @Autowired
    FavoriteMovieRepository favoriteMovieRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PackagesRepository packagesRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    //Lấy thông tin của 1 User
    @Override
    public UserDTO getUser(int id) {

        User user = userRepository.findById(id).orElse(null);
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setPassword(user.getPassword());
        userDTO.setNickName(user.getNickName());
        userDTO.setPhone(user.getPhone());

        return userDTO;
    }

    //Cập nhật thông tin User
    @Override
    public boolean updateUser(int userId, UserDTO userDTO) {

        User user = userRepository.findById(userId).orElse(null);

        user.setNickName(userDTO.getNickName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); 
        userRepository.save(user);

        return true;
    }
    
    //Search phim bằng tên phim 
    @Override
    // Tìm kiếm theo tên phim
    public List<MovieDTO> searchMoviesByName(String movieName) {
        List<Movie> movies = movieRepository.searchMoviesByName(movieName);
        return convertToMovieDTOList(movies);
    }

    // Tìm kiếm phim theo tên diễn viên
    @Override
    public List<MovieDTO> searchMoviesByActorName(String actorName) {
        List<Movie> movies = movieRepository.searchMoviesByActorName(actorName);
        return convertToMovieDTOList(movies);
    }

    // Hàm convert entity Movie sang MovieDTO
    private List<MovieDTO> convertToMovieDTOList(List<Movie> movies) {
        List<MovieDTO> movieDTOs = new ArrayList<>();

        for (Movie movie : movies) {
            List<String> actors = new ArrayList<>();
            movie.getMovie_actorList().forEach(ma -> actors.add(ma.getActor().getNameActor()));

            MovieDTO movieDTO = new MovieDTO(movie.getId(), movie.getMovieName(), movie.getPoster(), movie.getTotalEpisodes());
    
            movieDTOs.add(movieDTO);
        }

        return movieDTOs;
    }

    //Thêm phim yêu thích
    @Override
    public boolean addFavoriteMovie(FavoriteMovieDTO favoriteMovieDTO) {

        Favorite_Movie favoriteMovie = new Favorite_Movie();

        User user = userRepository.findById(favoriteMovieDTO.getUserId()).orElse(null);
        Movie movie = movieRepository.findById(favoriteMovieDTO.getMovieId()).orElse(null);

        Favorite_Movie existingFavorite = favoriteMovieRepository.findByUserIdAndMovieId(user.getId(), movie.getId());
        if (existingFavorite != null) {
            return false;
        }

        favoriteMovie.setUser(user);
        favoriteMovie.setMovie(movie);
        favoriteMovie.setActive(true);

        favoriteMovieRepository.save(favoriteMovie);

        return true;
    }

    //Lấy danh sách phim yêu thích của User
    @Override
    public List<MovieDTO> getFavoriteMovies(int userId) {
        
        List<Favorite_Movie> favorites = favoriteMovieRepository.findByUserId(userId);

        return favorites.stream()
                .filter(Favorite_Movie::isActive) //Chỉ lấy các phim có active là true
                .map(fav -> new MovieDTO(
                        fav.getMovie().getId(), 
                        fav.getMovie().getMovieName(), 
                        fav.getMovie().getPoster(), 
                        fav.getMovie().getTotalEpisodes()
                        ))
                .collect(Collectors.toList());
    }
    
    //Xoá phim yêu thích
    @Override
    public void deleteMovieFavourite(int userId, int movieId) {
        Favorite_Movie favorite = favoriteMovieRepository.findByUserIdAndMovieId(userId, movieId);
        favorite.setActive(false);
        favoriteMovieRepository.save(favorite);
    }

    //Đăng kí gói dịch vụ
    @Override
    public String registerSubscription(SubscriptionDTO subscriptionDTO) {
        User user = userRepository.findById(subscriptionDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Transaction latestTransaction = transactionRepository.findFirstByUserOrderByEndDateDesc(user)
                .orElse(null);

        // Kiểm tra gói dịch vụ hiện tại của người dùng
        if (latestTransaction != null && !latestTransaction.getEndDate().before(new Date())) {
            return "You currently have an active subscription until " + latestTransaction.getEndDate();
        }

        // Lấy gói dịch vụ
        Packages packages = packagesRepository.findById(subscriptionDTO.getPackageId())
                .orElseThrow(() -> new EntityNotFoundException("Package not found"));

        if (subscriptionDTO.isPaymentSuccess()) {
            
            java.sql.Date startDate = java.sql.Date.valueOf(LocalDate.now());
            java.sql.Date endDate = java.sql.Date.valueOf(LocalDate.now().plusDays(packages.getAccessDuration()));

            Transaction newTransaction = new Transaction();

            newTransaction.setUser(user);
            newTransaction.setPackages(packages);
            newTransaction.setStartDate(startDate);
            newTransaction.setEndDate(endDate);

            transactionRepository.save(newTransaction);

            return "Subscription successful! You can access content until " + endDate;
        } else {
            return "Payment failed. Please try again.";
        }
    }

     //Truy xuất lịch sử giao dịch
    @Override
    public List<TransactionHistoryDTO> getTransactionHistory(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Transaction> transactions = transactionRepository.findByUser(user);

        return transactions.stream()
                .map(transaction -> new TransactionHistoryDTO(
                        transaction.getPackages().getPackageName(),
                        transaction.getStartDate(),
                        transaction.getEndDate()
                ))
                .collect(Collectors.toList());
    }

}
