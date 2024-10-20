package com.webxemphim.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webxemphim.demo.entity.Comment;
import com.webxemphim.demo.entity.React;
import com.webxemphim.demo.entity.User;
import com.webxemphim.demo.enums.EmojiType;
import com.webxemphim.demo.repository.CommentRepository;
import com.webxemphim.demo.repository.ReactRepository;
import com.webxemphim.demo.repository.UserRepository;
import com.webxemphim.demo.service.imp.ReactServiceImp;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReactService implements ReactServiceImp{

    @Autowired
    private ReactRepository reactRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    //Thêm 1 react vào 1 Comment
    @Override
    public boolean addReact(int userId, int commentId, EmojiType reactType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        if (comment.getStatus() == 0) {  
            throw new IllegalStateException("Cannot react to a deleted comment");
        }

        React existingReact = reactRepository.findByComment_IdAndUser_Id(commentId, userId);

        if (existingReact != null) {

            existingReact.setReactType(reactType);
            reactRepository.save(existingReact);
            return false;

        } else {
            React newReact = new React();

            newReact.setStatus(1);
            newReact.setReactType(reactType);
            newReact.setComment(comment);
            newReact.setUser(user);

            reactRepository.save(newReact);
            return true;
        }
    }

    //Lấy ra số lượng từng loại React cho 1 Comment
    @Override
    public Map<EmojiType, Integer> getReact(int commentId) {
    
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        // Lấy danh sách các react có status = 1 cho comment đó
        List<React> reacts = reactRepository.findByCommentAndStatus(comment, 1);

        Map<EmojiType, Integer> reactCounts = new HashMap<>();

        // Đếm số lượng react theo loại
        for (React react : reacts) {
            reactCounts.put(react.getReactType(), reactCounts.getOrDefault(react.getReactType(), 0) + 1);
        }
        return reactCounts;
    }

     //Xoá 1 React ra khỏi 1 Comment
    @Override
    public boolean deleteReact(int userId, int commentId) {
        // Tìm React hiện có dựa trên commentId và userId
        React existingReact = reactRepository.findByComment_IdAndUser_Id(commentId, userId);
        
        if (existingReact != null && existingReact.getStatus() == 1) { 
            existingReact.setStatus(0); 
            reactRepository.save(existingReact); 

            return true;
        } else {
           return false;
        }
    }

}
