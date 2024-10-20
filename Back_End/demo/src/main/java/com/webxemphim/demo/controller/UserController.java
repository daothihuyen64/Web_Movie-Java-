package com.webxemphim.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webxemphim.demo.dto.FavoriteMovieDTO;
import com.webxemphim.demo.dto.MovieDTO;
import com.webxemphim.demo.dto.SubscriptionDTO;
import com.webxemphim.demo.dto.TransactionHistoryDTO;
import com.webxemphim.demo.dto.UserDTO;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.service.UserService;
import com.webxemphim.demo.service.imp.UserServiceImp;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    private UserService userService;
    
    //lấy thông tin 1 User
    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {

        return new ResponseEntity<>(userServiceImp.getUser(id), HttpStatus.OK);
        
    }
    
    //Cập nhật thông tin User
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {

        ResponseData responseData = new ResponseData();
        responseData.setSuccess(userService.updateUser(id, userDTO));

        return new ResponseEntity<>(responseData, HttpStatus.OK);
     
    }

    // Tìm kiếm theo tên phim
    @GetMapping("/search/name")
    public ResponseEntity<List<MovieDTO>> searchMoviesByName(@RequestParam String movieName) {

        List<MovieDTO> movies = userService.searchMoviesByName(movieName);
        return ResponseEntity.ok(movies);

    }

    // Tìm kiếm theo tên diễn viên
    @GetMapping("/search/actor")
    public ResponseEntity<List<MovieDTO>> searchMoviesByActorName(@RequestParam String actorName) {

        List<MovieDTO> movies = userService.searchMoviesByActorName(actorName);
        return ResponseEntity.ok(movies);

    }

    //Thêm phim yêu thích
    @PostMapping("/favoriteMovie/add")
    public ResponseEntity<?> addFavoriteMovie(@RequestBody FavoriteMovieDTO favoriteMovieDTO) {

        ResponseData responseData = new ResponseData();

        if(!userService.addFavoriteMovie(favoriteMovieDTO)) {
            responseData.setDesc("Movie is already in favorites.");
            responseData.setSuccess(false);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);

    }

    //Lấy danh sách phim yêu thích của 1 User
    @GetMapping("/favoriteMovie/{userId}")
    public ResponseEntity<List<MovieDTO>> getFavoriteMovies(@PathVariable int userId) {

        return new ResponseEntity<>(userService.getFavoriteMovies(userId), HttpStatus.OK);
        
    }

    //Xoá phim yêu thích
    @DeleteMapping("/favoriteMovie/delete")
    public ResponseEntity<?> deleteFavoriteMovie(@RequestParam Integer userId, @RequestParam Integer movieId) {
        ResponseData responseData = new ResponseData();

        userService.deleteMovieFavourite(userId, movieId);
        responseData.setDesc("Movie removed from favorites successfully.");

       return new ResponseEntity<>(responseData, HttpStatus.OK);
        
    }

    //Đăng kí gói dịch vụ
    @PostMapping("/transaction/register")
    public ResponseEntity<?> registerSubscription(@RequestBody SubscriptionDTO subscriptionDTO) {

        ResponseData responseData = new ResponseData();
        responseData.setDesc(userService.registerSubscription(subscriptionDTO));

        if (!responseData.getDesc().contains("successful")) {
            responseData.setSuccess(false);
        } 
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    //Truy xuất lịch sử giao dịch
    @GetMapping("/transaction/history/{userId}")
    public ResponseEntity<List<TransactionHistoryDTO>> getTransactionHistory(@PathVariable int userId) {

        List<TransactionHistoryDTO> history = userService.getTransactionHistory(userId);
        return ResponseEntity.ok(history);
        
    }

}
