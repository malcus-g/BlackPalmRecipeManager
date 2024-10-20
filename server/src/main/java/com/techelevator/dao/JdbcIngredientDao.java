package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Allergen;
import com.techelevator.model.Ingredient;
import com.techelevator.model.Recipe;
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
public class JdbcIngredientDao implements IngredientDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcIngredientDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Ingredient> getIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "SELECT id, name, allergen_id, image_path FROM ingredient ORDER BY name;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()){
                ingredients.add(mapRowToIngredient(results));
            }
            return ingredients;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }

    @Override
    public Ingredient getIngredientById(int id){
        Ingredient ingredient = null;
        String sql = "SELECT id, name, allergen_id, image_path FROM ingredient WHERE id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if(results.next()){
                ingredient = mapRowToIngredient(results);
            }
            return ingredient;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }

    @Override
    public Ingredient getIngredientByName(String name) {
        Ingredient ingredient = null;
        String sql = "SELECT id, name, allergen_id, image_path FROM ingredient WHERE name ILIKE ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
            if(results.next()){
                ingredient = mapRowToIngredient(results);
            }
            return ingredient;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database!", e);
        }
    }


    @Override
    public List<Ingredient> getIngredientsByAllergen(Allergen allergen) {
        return null;
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        Ingredient newIngredient = null;
        String sql = "INSERT INTO ingredient (name, allergen_id, image_path) " +
                        "VALUES (?, ?, ?) RETURNING id";
        try{
            int newIngredientId = jdbcTemplate.queryForObject(sql, int.class, ingredient.getName(),
                                    ingredient.getAllergenId(), ingredient.getImagePath());
            newIngredient = getIngredientById(newIngredientId);
            return newIngredient;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Error creating new ingredient - Data integrity violation");
        }
    }

    @Override
    public Ingredient updateIngredient(Ingredient ingredient) {
        return null;
    }

    @Override
    public int deleteIngredientById(int id) {
        return 0;
    }

    public Ingredient mapRowToIngredient(SqlRowSet rowSet){
        Ingredient ingredient = new Ingredient();
        ingredient.setId(rowSet.getInt("id"));
        ingredient.setName(rowSet.getString("name"));
        ingredient.setAllergenId(rowSet.getInt("allergen_id"));
        ingredient.setImagePath(rowSet.getString("image_path"));
        return ingredient;
    }
}
