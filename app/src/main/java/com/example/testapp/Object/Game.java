package com.example.testapp.Object;

import java.util.Set;

public class Game {
    private String id;
    private String name;
    private Set<String> genre;
    private double basePrice;
    private double discount;
    private double salePrice;
    private String icon;

    public Game(String id, String name, Set<String> genre, Float basePrice, String icon) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.basePrice = basePrice;
        this.icon = icon;
        this.discount = 1.0;
        this.setSalePrice();
    }

    public Game(String id, String name, Set<String> genre, Float basePrice, Float discount, String icon) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.basePrice = basePrice;
        this.discount = discount;
        this.setSalePrice();
        //this.icon = icon;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getGenre() {
        return genre;
    }

    public void setGenre(Set<String> genre) {
        this.genre = genre;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
        this.setSalePrice();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public  void setSalePrice() {
        this.salePrice = this.basePrice*this.discount;
    }

    public double getSalePrice() {
        return salePrice;
    }
}
