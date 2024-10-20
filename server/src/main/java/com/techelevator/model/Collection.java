package com.techelevator.model;

import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;

public class Collection {

    // Properties
    private int id;
    @NotEmpty
    private String name;
    private String imagePath;

    // Constructors
    public Collection(){

    }

    public Collection(int id, String name, String imagePath){
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
    }

    public Collection(String name){
        this.name = name;
    }

    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Methods


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collection that = (Collection) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(imagePath, that.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imagePath);
    }
}

