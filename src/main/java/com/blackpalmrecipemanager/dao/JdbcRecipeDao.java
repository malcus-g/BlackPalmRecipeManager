package com.blackpalmrecipemanager.dao;

import com.blackpalmrecipemanager.exception.DaoException;
import com.blackpalmrecipemanager.model.Category;
import com.blackpalmrecipemanager.model.Collection;
import com.blackpalmrecipemanager.model.Ingredient;
import com.blackpalmrecipemanager.model.Recipe;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcRecipeDao extends JdbcDao implements RecipeDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcRecipeDao(){
        setDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT id, name, collection_id, category_id, image_path " +
                        "FROM recipe ORDER BY id;";
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
    public Recipe getRecipeByName(String name) {
        return null;
    }

    @Override
    public Recipe getRecipeById(int id) {
        return null;
    }

    @Override
    public List<Recipe> getRecipesByIngredient(Ingredient ingredient) {
        return null;
    }

    @Override
    public List<Recipe> getRecipesByIngredient(List<Ingredient> ingredients) {
        return null;
    }

    @Override
    public List<Recipe> getRecipesByCollection(Collection collection) {
        return null;
    }

    @Override
    public List<Recipe> getRecipesByCategory(Category category) {
        return null;
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        return null;
    }

    @Override
    public Recipe updateRecipe(Recipe recipe) {
        return null;
    }

    @Override
    public int deleteRecipeById(int id) {
        return 0;
    }

    public Recipe mapRowToRecipe(SqlRowSet rowSet){
        Recipe recipe = new Recipe();
        recipe.setCategoryId(rowSet.getInt("id"));
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
