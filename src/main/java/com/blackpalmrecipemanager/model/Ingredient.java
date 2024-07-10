package com.blackpalmrecipemanager.model;

import java.util.Objects;

public class Ingredient {

    // Properties
    private int id;
    private String name;
    private int allergenId;
    private String imagePath;

    // Constructors
    public Ingredient(){

    }

    public Ingredient(int id, String name, int allergenId, String imagePath){
        this.id = id;
        this.name = name;
        this.allergenId = allergenId;
        this.imagePath = imagePath;
    }

    public Ingredient(String name, int allergenId, String imagePath){
        this.name = name;
        this.allergenId = allergenId;
        this.imagePath = imagePath;
    }

    // Getters & Setters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAllergenId() {
        return allergenId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAllergenId(int allergenId) {
        this.allergenId = allergenId;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return id == that.id && allergenId == that.allergenId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, allergenId);
    }
}
