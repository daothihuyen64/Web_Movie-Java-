package com.webxemphim.demo.service.imp;

import java.util.List;

import com.webxemphim.demo.dto.CommentDTO;
import com.webxemphim.demo.payload.request.CommentRequest;

public interface CommentServiceImp {
    CommentDTO addComment(CommentRequest request);
    List<CommentDTO> getCommentsByMovie(int movieId);
    void deleteComment(int commentId);
}
