package com.webxemphim.demo.dto;

import java.util.List;

public class DetailActorDTO {
    private int id;
    private String nameActor;
    private List<MovieDTO> movies;

    public DetailActorDTO() {}

    public DetailActorDTO(int id, String nameActor, List<MovieDTO> movies) {
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

    public List<MovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDTO> movies) {
        this.movies = movies;
    }
}
