package com.techelevator.dao;

import com.techelevator.model.Allergen;
import com.techelevator.model.Ingredient;
import com.techelevator.model.Recipe;

import java.util.List;
import java.util.Map;

public interface IngredientDao {

    // Get a list of all ingredients used in all recipes
    List<Ingredient> getIngredients();

    // Get ingredient by id
    Ingredient getIngredientById(int id);

    // Search ingredients by name
    // Exact match
    Ingredient getIngredientByName(String name);


    // Get list of ingredients that contain a specific allergen
    // If no ingredients contain the allergen, return an empty list
    List<Ingredient> getIngredientsByAllergen(Allergen allergen);

    // Create new ingredient
    Ingredient createIngredient(Ingredient ingredient);

    // Update existing ingredient
    Ingredient updateIngredient(Ingredient ingredient);

    // Delete existing ingredient
    // Remove ingredient from all recipes currently used in
    int deleteIngredientById(int id);
}
