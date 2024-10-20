package com.webxemphim.demo.dto;

public class SimpleMovieDTO {
    private int id;
    private String movieName;
    private String poster;
    
    public SimpleMovieDTO() {
    }
    public SimpleMovieDTO(int id, String movieName, String poster) {
        this.id = id;
        this.movieName = movieName;
        this.poster = poster;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
