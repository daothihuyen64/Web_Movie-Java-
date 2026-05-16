// package com.webxemphim.demo.repository;

// import com.webxemphim.demo.entity.Rating;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
// public interface RatingInterface extends JpaRepository<Rating, Integer> {
//     // Optional: Có thể thêm các truy vấn tùy chỉnh nếu cần
// }



package com.webxemphim.demo.repository;

import com.webxemphim.demo.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingInterface extends JpaRepository<Rating, Integer> {
    Optional<Rating> findByUserIdAndMovieId(int userId, int movieId);
}
