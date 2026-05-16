package com.webxemphim.demo.dto;

// import java.util.List;

public class SimpleReleaseYearDTO {
    private int id;
    private int year; // Thay đổi loại về int
    // private List<SimpleMovieDTO> movies;

    public SimpleReleaseYearDTO() {}
    public SimpleReleaseYearDTO(int id, int year) {
        this.id = id;
        this.year = year;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
