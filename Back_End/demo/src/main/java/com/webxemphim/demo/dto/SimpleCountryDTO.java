package com.webxemphim.demo.dto;


public class SimpleCountryDTO {
    private int id;
    private String countryName;
    public SimpleCountryDTO() {}
    public SimpleCountryDTO(int id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }

    // Getters và Setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


}
