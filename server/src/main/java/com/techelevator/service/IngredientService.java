package com.techelevator.service;

import com.techelevator.exception.ServiceException;
import com.techelevator.model.Ingredient;
import org.springframework.stereotype.Service;
import com.techelevator.dao.IngredientDao;

import java.util.List;

@Service
public class IngredientService {

    private IngredientDao ingredientDao;

    public IngredientService(IngredientDao ingredientDao){
        this.ingredientDao = ingredientDao;
    }

    public List<Ingredient> getIngredients(){
        return ingredientDao.getIngredients();
    }

    public Ingredient getIngredientbyId(int id){
        Ingredient ingredient = ingredientDao.getIngredientById(id);
        if(ingredient != null){
            return ingredient;
        }else{
            throw new ServiceException("No recipe found for id: " + id);
        }
    }

    public Ingredient createIngredient(Ingredient ingredient){
        Ingredient newIngredient = ingredientDao.createIngredient(ingredient);
        if(newIngredient != null){
            return newIngredient;
        }else{
            throw new ServiceException("Error creating new ingredient!");
        }
    }
}
