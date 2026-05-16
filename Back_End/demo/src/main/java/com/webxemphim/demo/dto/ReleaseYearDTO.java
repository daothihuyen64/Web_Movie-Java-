package com.webxemphim.demo.dto;

import java.util.List;

public class ReleaseYearDTO {
    private int id;
    private int year; // Thay đổi loại về int
    private List<SimpleMovieDTO> movies;

    public ReleaseYearDTO() {}
    public ReleaseYearDTO(int id, int year, List<SimpleMovieDTO> movies) {
        this.id = id;
        this.year = year;
        this.movies = movies;
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

    public List<SimpleMovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<SimpleMovieDTO> movies) {
        this.movies = movies;
    }
}
