package com.blackpalmrecipemanager.dao;

import com.blackpalmrecipemanager.model.Category;
import com.blackpalmrecipemanager.model.Collection;
import com.blackpalmrecipemanager.model.Ingredient;
import com.blackpalmrecipemanager.model.Recipe;
import java.util.List;

public interface RecipeDao {

    List<Recipe> getRecipes();
    Recipe getRecipeByName(String name);
    Recipe getRecipeById(int id);
    List<Recipe> getRecipesByIngredient(Ingredient ingredient);
    List<Recipe> getRecipesByIngredient(List<Ingredient> ingredients);
    List<Recipe> getRecipesByCollection(Collection collection);
    List<Recipe> getRecipesByCategory(Category category);
    Recipe createRecipe(Recipe recipe);
    Recipe updateRecipe(Recipe recipe);
    int deleteRecipeById(int id);
}
