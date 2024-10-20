package com.techelevator.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class IngredientRecipeDto {

    private int ingredientId;
    private int recipeId;
    private int allergenId;
    private String recipeName;
    @NotBlank
    private String ingredientName;
    @NotBlank
    private Double quantity;
    @NotBlank
    private String measurementUnit;
    private String imagePath;

    public IngredientRecipeDto(){

    }

    public IngredientRecipeDto(int ingredientId, int recipeId, int allergenId, String recipeName, String ingredientName, Double quantity, String measurementUnit, String imagePath) {
        this.ingredientId = ingredientId;
        this.recipeId = recipeId;
        this.allergenId = allergenId;
        this.recipeName = recipeName;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.measurementUnit = measurementUnit;
        this.imagePath = imagePath;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getAllergenId() {
        return allergenId;
    }

    public void setAllergenId(int allergenId) {
        this.allergenId = allergenId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientRecipeDto that = (IngredientRecipeDto) o;
        return getIngredientId() == that.getIngredientId() && getRecipeId() == that.getRecipeId() && getAllergenId() == that.getAllergenId() && Objects.equals(getRecipeName(), that.getRecipeName()) && Objects.equals(getIngredientName(), that.getIngredientName()) && Objects.equals(getQuantity(), that.getQuantity()) && Objects.equals(getMeasurementUnit(), that.getMeasurementUnit()) && Objects.equals(getImagePath(), that.getImagePath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIngredientId(), getRecipeId(), getAllergenId(), getRecipeName(), getIngredientName(), getQuantity(), getMeasurementUnit(), getImagePath());
    }
}
