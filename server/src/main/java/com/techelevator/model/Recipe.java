package com.techelevator.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

public class Recipe {

    // Properties
    private int id;
    @NotBlank
    private String name;
    @NotNull
    private int collectionId;
    @NotNull
    private int categoryId;
    private String imagePath;

    // Constructors
    public Recipe() {

    }

    public Recipe(int id, String name, int collectionId, int categoryId, String imagePath) {
        this.id = id;
        this.name = name;
        this.collectionId = collectionId;
        this.categoryId = categoryId;
        this.imagePath = imagePath;
    }

    public Recipe(String name, int collectionId, int categoryId, String imagePath, List<Ingredient> ingredients, List<Instruction> instructions) {
        this.name = name;
        this.collectionId = collectionId;
        this.categoryId = categoryId;
        this.imagePath = imagePath;
    }

    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return getId() == recipe.getId() && getCollectionId() == recipe.getCollectionId() && getCategoryId() == recipe.getCategoryId() && getName().equals(recipe.getName()) && Objects.equals(getImagePath(), recipe.getImagePath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCollectionId(), getCategoryId(), getImagePath());
    }
}
