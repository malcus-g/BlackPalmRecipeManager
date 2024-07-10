package com.blackpalmrecipemanager.controller;

import com.blackpalmrecipemanager.dao.RecipeDao;
import javax.sql.DataSource;

public class RecipeController {

    private RecipeDao recipeDao;

    public RecipeController(RecipeDao recipeDao){
        this.recipeDao = recipeDao;
    }
}
