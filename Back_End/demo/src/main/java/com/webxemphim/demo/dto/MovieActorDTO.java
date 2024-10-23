package com.webxemphim.demo.dto;

public class MovieActorDTO {
    private int id;
    private SimpleMovieDTO movie;
    private SimpleActorDTO actor;

    public MovieActorDTO() {}

    public MovieActorDTO(int id, SimpleMovieDTO movie, SimpleActorDTO actor) {
        this.id = id;
        this.movie = movie;
        this.actor = actor;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SimpleMovieDTO getMovie() {
        return movie;
    }

    public void setMovie(SimpleMovieDTO movie) {
        this.movie = movie;
    }

    public SimpleActorDTO getActor() {
        return actor;
    }

    public void setActor(SimpleActorDTO actor) {
        this.actor = actor;
    }
}
