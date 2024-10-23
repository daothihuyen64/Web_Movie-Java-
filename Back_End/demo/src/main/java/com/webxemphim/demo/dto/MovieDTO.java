package com.webxemphim.demo.dto;


public class MovieDTO {
    private int id;
    private String movieName;
    private String poster;
    private String trailer;
    private String description;
    private double ratingMean;
    private String director;
    private int totalEpisodes;
    private int views;
    private int status; // 1: active, 0: inactive
    private SimpleCountryDTO country;
    private SimpleReleaseYearDTO release_year;
    private SimpleGenreDTO genre;

    // Constructors
    public MovieDTO() {}

    public MovieDTO(int id, String movieName, String poster, String trailer, String description, double ratingMean,
                    String director, int totalEpisodes, int views, int status, SimpleCountryDTO country, SimpleReleaseYearDTO release_year, SimpleGenreDTO genre) {
        this.id = id;
        this.movieName = movieName;
        this.poster = poster;
        this.trailer = trailer;
        this.description = description;
        this.ratingMean = ratingMean;
        this.director = director;
        this.totalEpisodes = totalEpisodes;
        this.views = views;
        this.status = status;
        this.country = country;
        this.release_year = release_year;
        this.genre = genre;
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

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRatingMean() {
        return ratingMean;
    }

    public void setRatingMean(double ratingMean) {
        this.ratingMean = ratingMean;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
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
