package com.webxemphim.demo.enums;

public enum EmojiType {
    HEART("❤️"),       
    THUMBS_UP("👍"),   
    ANGRY("😡"),      
    SURPRISED("😮");   

    private final String emoji;

    EmojiType(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }
}
