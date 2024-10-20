package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Collection;
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
public class JdbcCollectionDao implements CollectionDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcCollectionDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<Collection> getCollections() {
        List<Collection> collections = new ArrayList<>();
        String sql = "SELECT id, name, image_path FROM collection ORDER BY name;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()){
                collections.add(mapRowToCollection(results));
            }
            return collections;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }

    @Override
    public Collection getCollectionById(int id){
        Collection collection = null;
        String sql = "SELECT id, name, image_path FROM collection " +
                        "WHERE id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if(results.next()){
                collection = mapRowToCollection(results);
            }
            return collection;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }

    @Override
    public List<Recipe> getCollectionRecipes(int collectionId){
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT id, name, category_id, collection_id, image_path " +
                        "FROM recipe WHERE collection_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
            while(results.next()){
                recipes.add(mapRowToRecipe(results));
            }
            return recipes;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }

    @Override
    public Collection getRecipeCollection(int id) {
        return null;
    }

    @Override
    public Collection createCollection(Collection collection) {
        Collection newCollection = null;
        String sql = "INSERT INTO collection (name) VALUES (?) RETURNING id;";
        try{
            int newCollectionId = jdbcTemplate.queryForObject(sql, int.class, collection.getName());
            newCollection = getCollectionById(newCollectionId);
            return newCollection;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }
    }

    @Override
    public Collection updateCollection(Collection collection) {
        return null;
    }

    @Override
    public int deleteCollectionById(int id) {
        String sql = "DELETE FROM collection WHERE id = ?;";
        try{
            return jdbcTemplate.update(sql, id);
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Error deleting collection - Data integrity violation", e);
        }
    }

    public Collection mapRowToCollection(SqlRowSet rowSet){
        Collection collection = new Collection();
        collection.setId(rowSet.getInt("id"));
        collection.setName(rowSet.getString("name"));
        collection.setImagePath(rowSet.getString("image_path"));
        return collection;
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
