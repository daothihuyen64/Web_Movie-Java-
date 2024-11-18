package com.webxemphim.demo.dto;

public class MovieDetailDTO {
    private int id;
    private String movieName;
    private String poster;
    private int status;
    private SimpleCountryDTO country;
    private SimpleReleaseYearDTO release_year;
    private SimpleGenreDTO genre;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SimpleCountryDTO getCountry() {
        return country;
    }

    public void setCountry(SimpleCountryDTO country) {
        this.country = country;
    }

    public SimpleReleaseYearDTO getRelease_year() {
        return release_year;
    }

    public void setRelease_year(SimpleReleaseYearDTO release_year) {
        this.release_year = release_year;
    }

    public SimpleGenreDTO getGenre() {
        return genre;
    }

    public void setGenre(SimpleGenreDTO genre) {
        this.genre = genre;
    }
}
