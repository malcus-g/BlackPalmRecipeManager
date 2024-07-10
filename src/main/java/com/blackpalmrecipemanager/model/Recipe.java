package com.blackpalmrecipemanager.model;

import java.util.List;
import java.util.Objects;

public class Recipe {

    // Properties
    private int id;
    private String name;
    private int collectionId;
    private int categoryId;
    private String imagePath;
    private List<Ingredient> ingredients;

    // Constructors
    public Recipe(){

    }

    public Recipe(int id, String name, int collectionId, int categoryId, String imagePath, List<Ingredient> ingredients){
        this.id = id;
        this.name = name;
        this.collectionId = collectionId;
        this.categoryId = categoryId;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
    }

    public Recipe(String name, int collectionId, int categoryId, String imagePath, List<Ingredient> ingredients){
        this.name = name;
        this.collectionId = collectionId;
        this.categoryId = categoryId;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
    }

    // Getters & Setters

    public int getId() {
        return id;
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

// Methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id && collectionId == recipe.collectionId && categoryId == recipe.categoryId && Objects.equals(name, recipe.name) && Objects.equals(ingredients, recipe.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, collectionId, categoryId, ingredients);
    }
}
