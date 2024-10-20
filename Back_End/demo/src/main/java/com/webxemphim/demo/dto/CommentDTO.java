package com.webxemphim.demo.dto;

import java.time.LocalDateTime;

public class CommentDTO {
    private int id;
    private String content;
    private LocalDateTime time;
    private String nickName;

    
    public CommentDTO(int id, String content, LocalDateTime time, String nickName) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.nickName = nickName;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
}
