package com.webxemphim.demo.dto;

import com.webxemphim.demo.enums.EmojiType;

public class ReactDTO {
    private EmojiType reactType;
    private int count;

    public ReactDTO(EmojiType reactType, int count) {
        this.reactType = reactType;
        this.count = count;
    }

    public EmojiType getReactType() {
        return reactType;
    }

    public void setReactType(EmojiType reactType) {
        this.reactType = reactType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
