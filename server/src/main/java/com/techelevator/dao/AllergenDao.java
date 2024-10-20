package com.techelevator.dao;

import com.techelevator.model.Allergen;
import com.techelevator.model.Recipe;

import java.util.List;

public interface AllergenDao {

    // Get a list of all potential allergens in recipes
    List<Allergen> getAllergens();

    // Get all allergens in a given recipe.
    // If no allergens are present, returns an empty list
    List<String> getRecipeAllergens(int recipeId);

    // Create a new allergen
    Allergen createAllergen(Allergen allergen);

    // Update an existing allergen
    Allergen updateAllergen(Allergen allergen);

    // Delete an existing allergen
    int deleteAllergenById(int id);
}
