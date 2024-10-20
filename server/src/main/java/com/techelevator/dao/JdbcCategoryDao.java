package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Category;
import com.techelevator.model.Recipe;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcCategoryDao implements CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCategoryDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name FROM category ORDER BY name;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                categories.add(mapRowToCategory(results));
            }
            return categories;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }

    @Override
    public Category getCategoryById(int id) {
        Category category = null;
        String sql = "SELECT id, name FROM category WHERE id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if(results.next()){
                category = mapRowToCategory(results);
            }
            return category;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }

    @Override
    public Category getRecipeCategory(int recipeId) {
        Category category = null;
        String sql = "SELECT c.id, c.name FROM recipe r " +
                "JOIN category c ON r.category_id = c.id WHERE r.id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, recipeId);
            if(results.next()){
                category = mapRowToCategory(results);
            }
            return category;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }

    @Override
    public List<Recipe> getCategoryRecipes(int categoryId) {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT id, name, category_id, collection_id, image_path " +
                        "FROM recipe WHERE category_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, categoryId);
            while (results.next()) {
                recipes.add(mapRowToRecipe(results));
            }
            return recipes;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }

    @Override
    public Category createCategory(Category category) {
        Category newCategory = null;
        String sql = "INSERT INTO category (name) VALUES (?) RETURNING id;";
        try{
            int newCategoryId = jdbcTemplate.queryForObject(sql, int.class, category.getName());
            newCategory = getCategoryById(newCategoryId);
            return newCategory;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Category not created - Data integrity violation", e);
        }
    }

    @Override
    public Category updateCategory(Category category) {
        String sql = "UPDATE category SET name = ? WHERE id = ?;";
        try{
            int rowsAffected = jdbcTemplate.update(sql, category.getName(), category.getId());
            if (rowsAffected == 0) {
                throw new DaoException("0 rows affected - Expected at least 1");
            }
            return getCategoryById(category.getId());
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Error updating category - Data integrity violation", e);
        }
    }

    @Override
    public int deleteCategoryById(int id) {
        String sql = "DELETE FROM category WHERE id = ?;";
        try{
            return jdbcTemplate.update(sql, id);
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Error deleting category - Data integrity violation", e);
        }
    }

    public Category mapRowToCategory(SqlRowSet rowSet){
        Category category = new Category();
        category.setId(rowSet.getInt("id"));
        category.setName(rowSet.getString("name"));
        return category;
    }

    public Recipe mapRowToRecipe(SqlRowSet rowSet){
        Recipe recipe = new Recipe();
        recipe.setId(rowSet.getInt("id"));
        recipe.setCategoryId(rowSet.getInt("category_id"));
        recipe.setCollectionId(rowSet.getInt("collection_id"));
        recipe.setName(rowSet.getString("name"));
        recipe.setImagePath(rowSet.getString("image_path"));
        return recipe;
    }
}
