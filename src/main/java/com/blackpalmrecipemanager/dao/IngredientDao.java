package com.blackpalmrecipemanager.dao;

import com.blackpalmrecipemanager.model.Ingredient;

import java.util.List;

public interface IngredientDao {

    List<Ingredient> getIngredients();
    Ingredient getIngredientById(int id);
    List<Ingredient> getIngredientsByName(String name);
    Ingredient createIngredient(Ingredient ingredient);
    Ingredient updateIngredient(Ingredient ingredient);
    int deleteIngredientById(int id);
}
