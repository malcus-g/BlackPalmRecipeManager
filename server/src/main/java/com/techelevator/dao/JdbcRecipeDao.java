package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcRecipeDao implements RecipeDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcRecipeDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT id, name, collection_id, category_id, image_path " +
                "FROM recipe ORDER BY name;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()){
                recipes.add(mapRowToRecipe(results));
            }
            return recipes;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }

    @Override
    public Recipe getRecipeById(int id){
        Recipe recipe = null;
        String sql = "SELECT id, name, collection_id, category_id, image_path " +
                        "FROM recipe WHERE id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if(results.next()){
                recipe = mapRowToRecipe(results);
            }
            return recipe;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }

    @Override
    public List<Recipe> getRecipesByName(String name) {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT * FROM recipe WHERE name ILIKE ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + name + "%");
            while (results.next()) {
                recipes.add(mapRowToRecipe(results));
            }
            return recipes;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }


    public List<Recipe> getRecipesByAllergen(String allergen){
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT r.id, r.name, collection_id, category_id, r.image_path " +
                        "FROM recipe r " +
                        "JOIN ingredient_recipe ir ON r.id = ir.recipe_id " +
                        "JOIN ingredient i ON ir.ingredient_id = i.id " +
                        "JOIN allergen a ON i.allergen_id = a.id " +
                        "WHERE a.name ILIKE '%?%';";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, allergen);
            while(results.next()){
                recipes.add(mapRowToRecipe(results));
            }
            return recipes;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }


    public List<Recipe> getRecipesByIngredient(String ingredient) {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT r.id, r.name, collection_id, category_id, r.image_path " +
                "FROM recipe r " +
                "JOIN ingredient_recipe ir ON r.id = ir.recipe_id " +
                "JOIN ingredient i ON ir.ingredient_id = i.id " +
                "WHERE i.name ILIKE '%?%';";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, ingredient);
            while(results.next()){
                recipes.add(mapRowToRecipe(results));
            }
            return recipes;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }


    public List<Recipe> getRecipesByCollection(String collection) {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT r.id, r.name, collection_id, category_id, r.image_path " +
                "FROM recipe r " +
                "JOIN collection c ON r.collection_id = c.id " +
                "WHERE c.name ILIKE '%?%';";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collection);
            while(results.next()){
                recipes.add(mapRowToRecipe(results));
            }
            return recipes;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }


    public List<Recipe> getRecipesByCategory(String category) {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT r.id, r.name, collection_id, category_id, r.image_path " +
                "FROM recipe r " +
                "JOIN category c ON r.category_id = c.id " +
                "WHERE c.name ILIKE '%?$';";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, category);
            while(results.next()){
                recipes.add(mapRowToRecipe(results));
            }
            return recipes;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        Recipe newRecipe = null;
        String sql = "INSERT INTO recipe (name, collection_id, category_id, image_path) " +
                        "VALUES (?,?,?,?) RETURNING id;";
        try{
            int newRecipeId = jdbcTemplate.queryForObject(sql, int.class, recipe.getName(), recipe.getCollectionId(), recipe.getCategoryId(), recipe.getImagePath());
            newRecipe = getRecipeById(newRecipeId);
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Error creating recipe - Data integrity violation", e);
        }
        return newRecipe;
    }

    @Override
    public Recipe updateRecipe(Recipe recipe) {
        Recipe updatedRecipe = null;
        String sql = "UPDATE recipe SET name = ?, collection_id = ?, category_id = ?, image_path = ? " +
                        "WHERE id = ?;";
        try{
            int rowsAffected = jdbcTemplate.update(sql, recipe.getName(), recipe.getCollectionId(),
                                                    recipe.getCategoryId(), recipe.getImagePath(), recipe.getId());
            if(rowsAffected == 0){
                throw new DaoException("0 rows updated, expected at least 1");
            }
            updatedRecipe = getRecipeById(recipe.getId());
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Error updating recipe - Data integrity violation", e);
        }
        return updatedRecipe;
    }

    @Override
    public int deleteRecipeById(int id) {
        String ingredientRecipeSql = "DELETE FROM ingredient_recipe WHERE recipe_id = ?;";
        String instructionSql = "DELETE FROM instruction WHERE recipe_id = ?;";
        String recipeSql = "DELETE FROM recipe WHERE id = ?;";
        try{
            jdbcTemplate.update(ingredientRecipeSql, id);
            jdbcTemplate.update(instructionSql, id);
            return jdbcTemplate.update(recipeSql, id);
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Error deleting the recipe - data integrity violation", e);
        }
    }

    public Recipe mapRowToRecipe(SqlRowSet rowSet){
        Recipe recipe = new Recipe();
        recipe.setId(rowSet.getInt("id"));
        recipe.setCategoryId(rowSet.getInt("category_id"));
        recipe.setName(rowSet.getString("name"));
        if(rowSet.getInt("collection_id") > 0) {
            recipe.setCollectionId(rowSet.getInt("collection_id"));
        }
        recipe.setCategoryId(rowSet.getInt("category_id"));
        if(rowSet.getString("image_path") != null) {
            recipe.setImagePath(rowSet.getString("image_path"));
        }
        return recipe;
    }
}

