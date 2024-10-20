package com.techelevator.dao;

import com.techelevator.model.Collection;
import com.techelevator.model.Recipe;

import java.util.List;

public interface CollectionDao {

    // Get a list of all collections
    List<Collection> getCollections();

    Collection getCollectionById(int id);

    List<Recipe> getCollectionRecipes(int collectionId);

    // Get the collection a recipe is in
    // If a recipe is not in a collection, return null
    Collection getRecipeCollection(int id);

    // Create a new collection
    Collection createCollection(Collection collection);

    // Updated an existing collection
    Collection updateCollection(Collection collection);

    // Delete an existing collection by ID
    // Should NOT delete the recipes in that collection.
    int deleteCollectionById(int id);
}
