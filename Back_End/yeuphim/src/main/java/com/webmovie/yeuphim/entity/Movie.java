/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webmovie.yeuphim.entity;

import jakarta.persistence.*;
import java.util.List;
/**
 *
 * @author Admin
 */
@Entity(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "poster")
    private String poster;

    @Column(name = "trailer")
    private String trailer;

    @Column(name = "description")
    private String description;

    @Column(name = "rating_mean")
    private int ratingMean;

    @Column(name = "director")
    private String director;

    @Column(name = "total_episodes")
    private int totalEpisodes;

    @Column(name = "views")
    private int views;

    @Column(name = "status")
    private int status;
    
    @ManyToOne
    @JoinColumn(name = "id_genre")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "id_release_year")
    private Release_Year release_year;

    @ManyToOne
    @JoinColumn(name = "id_country")
    private Country country;

    @OneToMany(mappedBy = "movie")
    private List<Episode> episodeList;

    @OneToMany(mappedBy = "movie")
    private List<Movie_Actor> movie_actorList;
    
    @OneToMany(mappedBy = "movie")
    private List<Rating> ratingList;
    
    @OneToMany(mappedBy = "movie")
    private List<Favorite_Movie> favourite_movieList;
    
    @OneToMany(mappedBy = "movie")
    private List<Comment> commentList;

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

    public int getRatingMean() {
        return ratingMean;
    }

    public void setRatingMean(int ratingMean) {
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Release_Year getRelease_year() {
        return release_year;
    }

    public void setRelease_year(Release_Year release_year) {
        this.release_year = release_year;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    public List<Movie_Actor> getMovie_actorList() {
        return movie_actorList;
    }

    public void setMovie_actorList(List<Movie_Actor> movie_actorList) {
        this.movie_actorList = movie_actorList;
    }

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    public List<Favorite_Movie> getFavourite_movieList() {
        return favourite_movieList;
    }

    public void setFavourite_movieList(List<Favorite_Movie> favourite_movieList) {
        this.favourite_movieList = favourite_movieList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
    
    
}
