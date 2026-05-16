package com.webxemphim.demo.dto;

import java.util.List;

public class GenreDTO {
    private int id;
    private String genreName;
    private List<SimpleMovieDTO> movieList; // Sử dụng SimpleMovieDTO để trả về danh sách phim đơn giản
    public GenreDTO() {}
    public GenreDTO(int id, String genreName, List<SimpleMovieDTO> movieList) {
        this.id = id;
        this.genreName = genreName;
        this.movieList = movieList;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public List<SimpleMovieDTO> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<SimpleMovieDTO> movieList) {
        this.movieList = movieList;
    }
}
