package com.webxemphim.demo.dto;

public class RatingDTO {
    private int id; // Thêm thuộc tính id
    private int star;

    public RatingDTO() {}

    public RatingDTO(int id, int star) {
        this.id = id;
        this.star = star;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
