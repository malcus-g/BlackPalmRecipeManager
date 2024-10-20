package com.techelevator.model;

import java.util.Objects;

public class IngredientRecipe {

    // Properties
    private int ingredientId;
    private int recipeId;
    private Double quantity;
    private String measurementUnit;

    // Constructors
    public IngredientRecipe(){}
    public IngredientRecipe(int ingredientId, int recipeId){
        this.ingredientId = ingredientId;
        this.recipeId = recipeId;
    }
    public IngredientRecipe(int ingredientId, int recipeId, String measurementUnit, Double quantity){
        this.ingredientId = ingredientId;
        this.recipeId = recipeId;
        this.measurementUnit = measurementUnit;
        this.quantity = quantity;
    }

    // Getters and Setters

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

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    // Methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientRecipe that = (IngredientRecipe) o;
        return getIngredientId() == that.getIngredientId() && getRecipeId() == that.getRecipeId() && Double.compare(that.getQuantity(), getQuantity()) == 0 && Objects.equals(getMeasurementUnit(), that.getMeasurementUnit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIngredientId(), getRecipeId(), getMeasurementUnit(), getQuantity());
    }

    @Override
    public String toString() {
        return "quantity: " + quantity +
                ", measurementUnit='" + measurementUnit + '\'' +
                '}';
    }
}
