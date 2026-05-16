package com.webxemphim.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webxemphim.demo.entity.Comment;
import com.webxemphim.demo.entity.React;
import com.webxemphim.demo.enums.EmojiType;

public interface ReactRepository extends JpaRepository<React, Integer>{
    List<React> findByComment_Id(int commentId);
    React findByComment_IdAndUser_Id(int commentId, int userId);
    List<React> findByComment_IdAndReactType(int commentId, EmojiType reactType);
    List<React> findByCommentAndStatus(Comment comment, int status);
}
