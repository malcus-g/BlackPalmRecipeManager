package com.techelevator.controller;

import com.techelevator.exception.DaoException;
import com.techelevator.exception.ServiceException;
import com.techelevator.model.*;
import com.techelevator.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/recipes")
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<Recipe> getAllRecipes(@RequestParam(required = false, defaultValue = "") String name){
        try{
            return recipeService.getRecipes(name);
        }catch(ServiceException | DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(path = "/{id}")
    public Recipe getRecipeById(@PathVariable int id){
        try{
            return recipeService.getRecipeById(id);
        }catch(ServiceException | DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(path = "/{id}/view")
    public RecipeDto viewRecipe(@PathVariable int id){
        try{
            return recipeService.getFullRecipe(id);
        }catch(ServiceException | DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(path = "/{id}/ingredients")
    public List<IngredientRecipeDto> getRecipeIngredients(@PathVariable int id){
        try{
            return recipeService.getRecipeIngredients(id);
        }catch(ServiceException | DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(path = "/{id}/instructions")
    public List<Instruction> getRecipeInstructions(@PathVariable int id){
        try{
            return recipeService.getRecipeInstructions(id);
        }catch(ServiceException | DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Recipe createRecipe(@RequestBody @Valid Recipe recipe){
        try{
            return recipeService.createRecipe(recipe);
        }catch(ServiceException | DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @PutMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Recipe updateRecipe(@PathVariable int id, @Valid @RequestBody Recipe recipe){
        try{
            return recipeService.updateRecipe(id, recipe);
        }catch(ServiceException | DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping(path = "/{id}/ingredients")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RecipeDto addIngredientToRecipe(@PathVariable int id, @Valid @RequestBody IngredientRecipe ingredient){
        try{
            return recipeService.addIngredientToRecipe(id, ingredient);
        }catch(DaoException | ServiceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}/ingredients/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RecipeDto deleteIngredientFromRecipe(@PathVariable int id, @PathVariable int ingredientId){
        try{
            return recipeService.deleteIngredientFromRecipe(id, ingredientId);
        }catch(DaoException | ServiceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping(path = "/{id}/instructions")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RecipeDto addInstructionToRecipe(@PathVariable int id, @Valid @RequestBody Instruction instruction){
        try{
            return recipeService.addInstructionToRecipe(id, instruction);
        }catch(DaoException | ServiceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}/instructions/{stepNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RecipeDto deleteInstructionFromRecipe(@PathVariable int id, @PathVariable int stepNumber){
        try{
            return recipeService.deleteInstructionFromRecipe(id, stepNumber);
        }catch(DaoException | ServiceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable int id){
        try{
            recipeService.deleteRecipe(id);
        }catch(ServiceException | DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
