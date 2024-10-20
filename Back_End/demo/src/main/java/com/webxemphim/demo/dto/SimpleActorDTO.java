package com.webxemphim.demo.dto;

public class SimpleActorDTO {
    private int id;
    private String nameActor;

    public SimpleActorDTO() {}

    public SimpleActorDTO(int id, String nameActor) {
        this.id = id;
        this.nameActor = nameActor;
    }

    // Getters and Setters
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setNameActor(String nameActor) {
        this.nameActor = nameActor;
    }
    public String getNameActor() {
        return nameActor;
    }
}
