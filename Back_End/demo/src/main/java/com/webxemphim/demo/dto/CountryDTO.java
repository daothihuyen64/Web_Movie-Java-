package com.webxemphim.demo.dto;

import java.util.List;

public class CountryDTO {
    private int id;
    private String countryName;
    private List<SimpleMovieDTO> movieList; // Danh sách phim
    public CountryDTO() {}
    public CountryDTO(int id, String countryName, List<SimpleMovieDTO> movieList) {
        this.id = id;
        this.countryName = countryName;
        this.movieList = movieList;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<SimpleMovieDTO> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<SimpleMovieDTO> movieList) {
        this.movieList = movieList;
    }
}
