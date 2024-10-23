package com.webxemphim.demo.dto;

public class PackagesDTO {
    private int id;
    private String packageName;
    private double price;
    private int accessDuration;
    private String description;

    // Constructors
    public PackagesDTO() {}

    public PackagesDTO(int id, String packageName, double price, int accessDuration, String description) {
        this.id = id;
        this.packageName = packageName;
        this.price = price;
        this.accessDuration = accessDuration;
        this.description = description;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAccessDuration() {
        return accessDuration;
    }

    public void setAccessDuration(int accessDuration) {
        this.accessDuration = accessDuration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
