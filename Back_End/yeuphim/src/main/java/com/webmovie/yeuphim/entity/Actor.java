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
