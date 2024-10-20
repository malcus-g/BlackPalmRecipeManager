package com.techelevator.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RecipeDto {

    private String name;
    private String category;
    private String imagePath;
    private Map<String, String> ingredients;
    private List<String> allergens;
    private Map<Integer, String> instructions;

    public RecipeDto(){

    }

    public RecipeDto(String category, String name, String imagePath, Map<String, String> ingredients, List<String> allergens, Map<Integer, String> instructions) {
        this.category = category;
        this.name = name;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
        this.allergens = allergens;
        this.instructions = instructions;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<String> allergens) {
        this.allergens = allergens;
    }

    public Map<Integer, String> getInstructions() {
        return instructions;
    }

    public void setInstructions(Map<Integer, String> instructions) {
        this.instructions = instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeDto recipeDto = (RecipeDto) o;
        return Objects.equals(getName(), recipeDto.getName()) && Objects.equals(getCategory(), recipeDto.getCategory()) && Objects.equals(getImagePath(), recipeDto.getImagePath()) && Objects.equals(getIngredients(), recipeDto.getIngredients()) && Objects.equals(getAllergens(), recipeDto.getAllergens()) && Objects.equals(getInstructions(), recipeDto.getInstructions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCategory(), getImagePath(), getIngredients(), getAllergens(), getInstructions());
    }
}
