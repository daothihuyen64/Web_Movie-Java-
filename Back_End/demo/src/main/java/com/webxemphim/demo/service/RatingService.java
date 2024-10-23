package com.webxemphim.demo.service;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.dto.RatingDTO;
import com.webxemphim.demo.entity.Movie;
import com.webxemphim.demo.entity.Rating;
import com.webxemphim.demo.entity.User;
import com.webxemphim.demo.repository.MovieInterface;
import com.webxemphim.demo.repository.RatingInterface;
import com.webxemphim.demo.repository.UserInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingInterface ratingRepository;

    @Autowired
    private MovieInterface movieRepository;

    @Autowired
    private UserInterface userRepository;

    @Transactional
    public ResponseData addOrUpdateRating(int userId, int movieId, int star) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Movie> movie = movieRepository.findById(movieId);
    
        ResponseData responseData = new ResponseData();
    
        if (user.isEmpty() || movie.isEmpty()) {
            responseData.setDesc("Người dùng hoặc phim không tồn tại!");
            responseData.setStatus(404);
            responseData.setSuccess(false);
            return responseData;
        }
        
        // Kiểm tra nếu movie có status = 0 thì không cho phép đánh giá
        if (movie.get().getStatus() == 0) {
            responseData.setDesc("Không thể đánh giá cho phim đã bị vô hiệu hóa!");
            responseData.setStatus(400);  // Bad Request
            responseData.setSuccess(false);
            return responseData;
        }

        Optional<Rating> existingRating = ratingRepository.findByUserIdAndMovieId(userId, movieId);
        if (existingRating.isPresent()) {
            Rating rating = existingRating.get();
            rating.setStar(star);  // Cập nhật số sao
            ratingRepository.save(rating);
            responseData.setDesc("Cập nhật đánh giá thành công!");
            responseData.setData(mapToDTO(rating));
        } 
        else {
            Rating newRating = new Rating();
            newRating.setUser(user.get());
            newRating.setMovie(movie.get());
            newRating.setStar(star);
            ratingRepository.save(newRating);
            responseData.setDesc("Thêm đánh giá thành công!");
            responseData.setData(mapToDTO(newRating));
        }
    
        updateMovieRatingMean(movie.get());
        responseData.setStatus(200);
        responseData.setSuccess(true);
        return responseData;
    }
    
    /**
     * Tính toán lại rating_mean cho một bộ phim, làm tròn kết quả < .5 thì làm tròn xuống, ngược lại làm tròn lên.
     */
    private void updateMovieRatingMean(Movie movie) {
        // Lấy tổng số sao và số lượng đánh giá cho bộ phim này
        int sumStars = movie.getRatingList().stream()
                .mapToInt(Rating::getStar)
                .sum();
        int totalRatings = movie.getRatingList().size();

        // Tính rating_mean (trung bình)
        if (totalRatings != 0) {
            double rawRatingMean = (double) sumStars / totalRatings;
            int roundedRatingMean = (int) Math.round(rawRatingMean); // Làm tròn
            movie.setRatingMean(roundedRatingMean);
        } 
        else {
            movie.setRatingMean(0);
        }

        // Lưu lại Movie sau khi cập nhật
        movieRepository.save(movie);
    }

    private RatingDTO mapToDTO(Rating rating) {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setId(rating.getId());
        ratingDTO.setStar(rating.getStar());
        return ratingDTO; // Trả về DTO thay vì Entity
    }
}