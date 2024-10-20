package com.techelevator.service;

import com.techelevator.dao.*;
import com.techelevator.exception.ServiceException;
import com.techelevator.model.*;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService {
    private UserDao userDao;
    private RecipeDao recipeDao;
    private IngredientDao ingredientDao;
    private CategoryDao categoryDao;
    private CollectionDao collectionDao;
    private AllergenDao allergenDao;
    private InstructionDao instructionDao;
    private IngredientRecipeDao ingredientRecipeDao;

    public RecipeService(UserDao userDao, RecipeDao recipeDao, IngredientDao ingredientDao, CategoryDao categoryDao, CollectionDao collectionDao, AllergenDao allergenDao, InstructionDao instructionDao, IngredientRecipeDao ingredientRecipeDao){
        this.userDao = userDao;
        this.recipeDao = recipeDao;
        this.ingredientDao = ingredientDao;
        this.categoryDao = categoryDao;
        this.collectionDao = collectionDao;
        this.allergenDao = allergenDao;
        this.instructionDao = instructionDao;
        this.ingredientRecipeDao = ingredientRecipeDao;
    }

    public List<Recipe> getRecipes(String name){
        if(name == null) {
            return recipeDao.getRecipes();
        }else{
            return recipeDao.getRecipesByName(name);
        }
    }

    public Recipe getRecipeById(int id){
        if(recipeDao.getRecipeById(id) != null) {
            return recipeDao.getRecipeById(id);
        }else {
            throw new ServiceException("No recipe found for ID " + id);
        }
    }

    public List<IngredientRecipeDto> getRecipeIngredients(int id){
        if(ingredientRecipeDao.getRecipeIngredients(id) != null) {
            return ingredientRecipeDao.getRecipeIngredients(id);
        }else{
            throw new ServiceException("No ingredients found for recipe at ID: " + id);
        }
    }

    public List<Instruction> getRecipeInstructions(int id){
        if(instructionDao.getRecipeInstructions(id) != null){
            return instructionDao.getRecipeInstructions(id);
        }else{
            throw new ServiceException("No instructions found for recipe at ID: " + id);
        }
    }

    public RecipeDto getFullRecipe(int id){
        RecipeDto recipe = new RecipeDto();
        // Set name and category
        recipe.setName(recipeDao.getRecipeById(id).getName());
        recipe.setCategory(categoryDao.getRecipeCategory(id).getName());

        // Set ingredients
        Map<String, String> ingredients = new HashMap<>();
        List<IngredientRecipeDto> recipeIngredients = ingredientRecipeDao.getRecipeIngredients(id);
        for(IngredientRecipeDto ingredientRecipe : recipeIngredients){
            if(ingredientDao.getIngredientById(ingredientRecipe.getIngredientId()) != null) {
                ingredients.put(ingredientDao.getIngredientById(ingredientRecipe.getIngredientId()).getName(),
                        ingredientRecipe.getQuantity() + ingredientRecipe.getMeasurementUnit());
            }else{
                throw new ServiceException("No ingredient found for ID " + ingredientRecipe.getIngredientId());
            }
        }
        recipe.setIngredients(ingredients);

        // Set allergens
        recipe.setAllergens(allergenDao.getRecipeAllergens(id));
        // Set instructions
        Map<Integer, String> instructions = new HashMap<>();
        List<Instruction> rawInstructions = instructionDao.getRecipeInstructions(id);
        for(Instruction instruction : rawInstructions){
            instructions.put(instruction.getStepNumber(), instruction.getStepText());
        }
        recipe.setInstructions(instructions);

        return recipe;
    }

    public void deleteRecipe(int id){
        if(recipeDao.deleteRecipeById(id) != 0) {
            recipeDao.deleteRecipeById(id);
        }else{
            throw new ServiceException("0 recipes deleted - Expected at least 1");
        }
    }

    public Recipe createRecipe(Recipe newRecipe){
        Recipe recipe = recipeDao.createRecipe(newRecipe);
        if(recipe == null){
            throw new ServiceException("Error creating new recipe!");
        }else{
            return recipe;
        }
    }

    public Recipe updateRecipe(int id, Recipe recipe){
        recipe.setId(id);
        Recipe newRecipe = recipeDao.updateRecipe(recipe);
        if(!recipe.equals(newRecipe)){
            throw new ServiceException("Error - Recipe not properly updated!");
        }
        return newRecipe;
    }

    public RecipeDto addIngredientToRecipe(int id, IngredientRecipe ingredient){
        ingredient.setRecipeId(id);
        ingredientRecipeDao.addIngredientToRecipe(ingredient);
        return getFullRecipe(id);
    }

    public RecipeDto deleteIngredientFromRecipe(int id, int ingredientId){
        int rowsAffected = ingredientRecipeDao.deleteIngredientFromRecipe(id, ingredientId);
        if(rowsAffected == 0){
            throw new ServiceException("Error deleting ingredient from recipe! 0 rows affected");
        }
        return getFullRecipe(id);
    }

    public RecipeDto addInstructionToRecipe(int id, Instruction instruction){
        instruction.setRecipeId(id);
        instructionDao.createInstruction(instruction);
        return getFullRecipe(id);
    }

    public RecipeDto deleteInstructionFromRecipe(int id, int stepNumber){
        int rowsAffected = instructionDao.deleteInstruction(id, stepNumber);
        if (rowsAffected == 0) {
            throw new ServiceException("Error deleting instruction! 0 rows affected");
        }
        return getFullRecipe(id);
    }
}
