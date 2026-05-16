package com.webxemphim.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webxemphim.demo.dto.CommentDTO;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.payload.request.CommentRequest;
import com.webxemphim.demo.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //Thêm 1 Comment vào 1 bộ phim
    @PostMapping("/add")
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentRequest request) {
        return ResponseEntity.ok(commentService.addComment(request));
    }

    //Lấy ra toàn bộ Comment của 1 bộ phim
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByMovie(@PathVariable Integer movieId) {
        return ResponseEntity.ok(commentService.getCommentsByMovie(movieId));
    }

    //Xoá 1 Comment trong 1 bộ phim
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentId) {
       
        ResponseData responseData = new ResponseData();
        commentService.deleteComment(commentId);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
