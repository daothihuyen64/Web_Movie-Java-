package com.webxemphim.demo.repository;

import com.webxemphim.demo.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreInterface extends JpaRepository<Genre, Integer> {
    // Các truy vấn khác có thể được thêm tại đây nếu cần
}
