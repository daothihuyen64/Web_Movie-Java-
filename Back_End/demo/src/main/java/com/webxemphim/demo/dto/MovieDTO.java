package com.webxemphim.demo.dto;

public class MovieDTO {
    private Integer id;
    private String name;
    private String poster;
    private int totalEpisodes;
    
    public MovieDTO(Integer id, String name, String poster, int totalEpisodes) {
        this.id = id;
        this.name = name;
        this.poster = poster;
        this.totalEpisodes = totalEpisodes;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public int getTotalEpisodes() {
        return totalEpisodes;
    }
    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    
    
}
