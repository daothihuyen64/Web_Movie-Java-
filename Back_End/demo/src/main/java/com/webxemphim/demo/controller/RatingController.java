package com.webxemphim.demo.controller;


import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    /**
     * API để thêm hoặc cập nhật đánh giá.
     * @param userId ID của người dùng
     * @param movieId ID của bộ phim
     * @param star Số sao đánh giá
     * @return Thông báo kết quả
     */
    @PostMapping("/addOrUpdate")
    public ResponseEntity<?> addOrUpdateRating(
            @RequestParam int userId,
            @RequestParam int movieId,
            @RequestParam int star) {
        ResponseData responseData = ratingService.addOrUpdateRating(userId, movieId, star);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}

