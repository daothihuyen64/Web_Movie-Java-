package com.webxemphim.demo.service.imp;

import java.util.Map;

import com.webxemphim.demo.enums.EmojiType;

public interface ReactServiceImp {
    boolean addReact(int userId, int commentId, EmojiType reactType);
    Map<EmojiType, Integer> getReact(int commentId);
    boolean deleteReact(int userId, int commentId);
}
