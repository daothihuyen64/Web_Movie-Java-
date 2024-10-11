package com.webxemphim.demo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity(name = "actor")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_actor")
    private String nameActor;

    @OneToMany(mappedBy = "actor")
    private List<Movie_Actor> movie_actorList;

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

    public List<Movie_Actor> getMovie_actorList() {
        return movie_actorList;
    }

    public void setMovie_actorList(List<Movie_Actor> movie_actorList) {
        this.movie_actorList = movie_actorList;
    }
}
