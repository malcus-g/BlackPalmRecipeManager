package com.techelevator.dao;


import com.techelevator.model.IngredientRecipe;
import com.techelevator.model.IngredientRecipeDto;
import java.util.List;

public interface IngredientRecipeDao {

    List<IngredientRecipeDto> getRecipeIngredients(int recipeId);
    void addIngredientToRecipe(IngredientRecipe ingredient);
    int deleteIngredientFromRecipe(int recipeId, int ingredientId);

}
