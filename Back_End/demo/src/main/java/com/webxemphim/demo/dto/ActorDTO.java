package com.webxemphim.demo.dto;

import java.util.List;

public class ActorDTO {
    private int id;
    private String nameActor;
    private List<SimpleMovieDTO> movies;

    public ActorDTO() {}

    public ActorDTO(int id, String nameActor, List<SimpleMovieDTO> movies) {
        this.id = id;
        this.nameActor = nameActor;
        this.movies = movies;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameActor() {
        return nameActor;
    }

    public void setNameActor(String nameActor) {
        this.nameActor = nameActor;
    }

    public List<SimpleMovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<SimpleMovieDTO> movies) {
        this.movies = movies;
    }
}
