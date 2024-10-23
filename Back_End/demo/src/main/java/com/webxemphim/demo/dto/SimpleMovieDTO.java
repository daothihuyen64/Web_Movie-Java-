package com.webxemphim.demo.dto;

public class SimpleMovieDTO {
    private int id;
    private String movieName;
    private String poster;
    private int rating_mean;
    
    public SimpleMovieDTO() {
    }
    public SimpleMovieDTO(int id, String movieName, String poster, int rating_mean) {
        this.id = id;
        this.movieName = movieName;
        this.poster = poster;
        this.rating_mean = rating_mean;
    }

    // Getters and Setters
    public int getRatingMean() {
        return rating_mean;
    }
    public void setRatingMean(int star) {
        this.rating_mean = star;
    }
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
