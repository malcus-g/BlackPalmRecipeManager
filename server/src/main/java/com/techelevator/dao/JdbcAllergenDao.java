package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Allergen;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcAllergenDao implements AllergenDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAllergenDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Allergen> getAllergens() {
        return null;
    }

    @Override
    public List<String> getRecipeAllergens(int recipeId) {
        List<String> allergens = new ArrayList<>();
        String sql = "SELECT a.name FROM allergen a JOIN ingredient i ON a.id = i.allergen_id " +
                "JOIN ingredient_recipe ir ON i.id = ir.ingredient_id WHERE ir.recipe_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, recipeId);
            while(results.next()){
                allergens.add(results.getString("name"));
            }
            return allergens;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }

    @Override
    public Allergen createAllergen(Allergen allergen) {
        return null;
    }

    @Override
    public Allergen updateAllergen(Allergen allergen) {
        return null;
    }

    @Override
    public int deleteAllergenById(int id) {
        return 0;
    }
}
