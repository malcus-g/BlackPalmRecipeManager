package com.techelevator.dao;

import com.techelevator.model.*;

import java.util.List;

public interface RecipeDao {

    // Get a list of all recipes
    List<Recipe> getRecipes();

    // Search for recipes by ID

    Recipe getRecipeById(int id);

    // Search for recipes by name. Case insensitive
    // Returns all recipes containing the search string
    List<Recipe> getRecipesByName(String name);

    // Create a new recipe
    Recipe createRecipe(Recipe recipe);

    // Update an existing recipe
    Recipe updateRecipe(Recipe recipe);

    // Delete an existing recipe by ID
    int deleteRecipeById(int id);
}
