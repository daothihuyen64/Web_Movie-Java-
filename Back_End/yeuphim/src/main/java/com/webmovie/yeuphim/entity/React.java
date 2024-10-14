/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webmovie.yeuphim.entity;

import jakarta.persistence.*;

@Entity(name = "react")
public class React {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "react_type")
    private int reactType;

    @ManyToOne
    @JoinColumn(name = "id_comment")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReactType() {
        return reactType;
    }

    public void setReactType(int reactType) {
        this.reactType = reactType;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    
}
