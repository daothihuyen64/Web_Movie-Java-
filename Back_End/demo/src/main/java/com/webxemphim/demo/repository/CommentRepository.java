package com.webxemphim.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.webxemphim.demo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
    List<Comment> findByMovieIdAndStatus(int movie, int status);
}
