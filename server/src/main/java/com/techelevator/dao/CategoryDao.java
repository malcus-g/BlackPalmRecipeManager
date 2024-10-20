package com.techelevator.dao;

import com.techelevator.model.Category;
import com.techelevator.model.Recipe;

import java.util.List;

public interface CategoryDao {

    // Get a list of all existing categories
    List<Category> getCategories();

    // Get category by id
    Category getCategoryById(int id);

    // Get the category for a recipe
    Category getRecipeCategory(int recipeId);

    // Get the recipes in a category
    List<Recipe> getCategoryRecipes(int categoryId);

    // Create new category
    Category createCategory(Category category);

    // Update existing category
    Category updateCategory(Category category);

    // Delete existing category
    // Should NOT delete the recipes in that category.
    // Recipes in category to be deleted should be moved to category 0 - MISC
    int deleteCategoryById(int id);
}
