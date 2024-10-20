package com.webxemphim.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webxemphim.demo.dto.CommentDTO;
import com.webxemphim.demo.entity.Comment;
import com.webxemphim.demo.entity.Movie;
import com.webxemphim.demo.entity.User;
import com.webxemphim.demo.payload.request.CommentRequest;
import com.webxemphim.demo.repository.CommentRepository;
import com.webxemphim.demo.repository.MovieRepository;
import com.webxemphim.demo.repository.UserRepository;
import com.webxemphim.demo.service.imp.CommentServiceImp;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentService implements CommentServiceImp{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    //Thêm 1 Comment vào 1 bộ phim
    @Override
    public CommentDTO addComment(CommentRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Movie movie = movieRepository.findById(request.getMovieId())
            .orElseThrow(() -> new EntityNotFoundException("Movie not found"));

        Comment comment = new Comment();
        
        comment.setContent(request.getContent());
        comment.setStatus(1);
        comment.setUser(user);
        comment.setMovie(movie);
        comment.setTime(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return new CommentDTO(savedComment.getId(), savedComment.getContent(), savedComment.getTime(), user.getNickName());
    }
     
    //Lấy toàn bộ Comment của 1 bộ phim
    @Override
    public List<CommentDTO> getCommentsByMovie(int movieId) {
        List<Comment> comments = commentRepository.findByMovieIdAndStatus(movieId, 1);
        return comments.stream()
                .map(comment -> new CommentDTO(comment.getId(), comment.getContent(), comment.getTime(), comment.getUser().getNickName()))
                .collect(Collectors.toList());
    }

    //Xoá 1 Comment của 1 bộ phim
    @Override
    public void deleteComment(int commentId) {
        Comment comment = commentRepository.findById(commentId)
                          .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        comment.setStatus(0);
        commentRepository.save(comment);
    }

}
