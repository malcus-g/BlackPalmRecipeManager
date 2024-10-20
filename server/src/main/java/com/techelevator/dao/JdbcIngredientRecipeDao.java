package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.IngredientRecipeDto;
import com.techelevator.model.IngredientRecipe;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcIngredientRecipeDao implements IngredientRecipeDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcIngredientRecipeDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<IngredientRecipeDto> getRecipeIngredients(int recipeId) {
        List<IngredientRecipeDto> ingredients = new ArrayList<>();
        String sql = "SELECT ingredient_id, recipe_id, allergen_id, i.name, quantity, measurement_unit " +
                        "FROM ingredient_recipe ir JOIN ingredient i ON ir.ingredient_id = i.id " +
                        "WHERE recipe_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, recipeId);
            while (results.next()) {
                ingredients.add(mapRowToIngredientRecipeDto(results));
            }
            return ingredients;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }

    @Override
    public void addIngredientToRecipe(IngredientRecipe ingredient) {
        String sql = "INSERT INTO ingredient_recipe (ingredient_id, recipe_id, quantity, measurement_unit) " +
                        "VALUES (?, ?, ?, ?);";
        try{
            jdbcTemplate.update(sql, ingredient.getIngredientId(), ingredient.getRecipeId(),
                                ingredient.getQuantity(), ingredient.getMeasurementUnit());
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Error adding ingredient to recipe - Data integrity violation", e);
        }
    }


    @Override
    public int deleteIngredientFromRecipe(int recipeId, int ingredientId) {
        String sql = "DELETE FROM ingredient_recipe WHERE recipe_id = ? AND ingredient_id = ?;";
        try{
            return jdbcTemplate.update(sql, recipeId, ingredientId);
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database");
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Error deleting ingredient from recipe - Data integrity violation", e);
        }
    }

    public IngredientRecipe mapRowToIngredientRecipe(SqlRowSet rowSet){
        IngredientRecipe ingredientRecipe = new IngredientRecipe();
        ingredientRecipe.setIngredientId(rowSet.getInt("ingredient_id"));
        ingredientRecipe.setRecipeId(rowSet.getInt("recipe_id"));
        ingredientRecipe.setQuantity(rowSet.getDouble("quantity"));
        ingredientRecipe.setMeasurementUnit(rowSet.getString("measurement_unit"));
        return ingredientRecipe;
    }

    public IngredientRecipeDto mapRowToIngredientRecipeDto(SqlRowSet rowSet){
        IngredientRecipeDto ingredientRecipeDto = new IngredientRecipeDto();
        ingredientRecipeDto.setIngredientId(rowSet.getInt("ingredient_id"));
        ingredientRecipeDto.setRecipeId(rowSet.getInt("recipe_id"));
        ingredientRecipeDto.setAllergenId(rowSet.getInt("allergen_id"));
        ingredientRecipeDto.setIngredientName(rowSet.getString("name"));
        ingredientRecipeDto.setQuantity(rowSet.getDouble("quantity"));
        ingredientRecipeDto.setMeasurementUnit(rowSet.getString("measurement_unit"));
        return ingredientRecipeDto;
    }
}
