package com.techelevator.service;

import com.techelevator.dao.CollectionDao;
import com.techelevator.exception.ServiceException;
import com.techelevator.model.Collection;
import com.techelevator.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {

    private CollectionDao collectionDao;

    public CollectionService(CollectionDao collectionDao){
        this.collectionDao = collectionDao;
    }

    public List<Collection> getCollections(){
        List<Collection> collections = collectionDao.getCollections();
        if(!collections.isEmpty()){
            return collections;
        }else{
            throw new ServiceException("Error getting collections");
        }
    }

    public Collection getCollectionById(int id){
        if(collectionDao.getCollectionById(id) != null){
            return collectionDao.getCollectionById(id);
        }else{
            throw new ServiceException("No collection found for ID " + id);
        }
    }

    public List<Recipe> getCollectionRecipes(int collectionId){
        List<Recipe> recipes = collectionDao.getCollectionRecipes(collectionId);
        if(!recipes.isEmpty()){
            return recipes;
        }else{
            throw new ServiceException("Error getting recipes for collection at ID: " + collectionId);
        }
    }

    public Collection createCollection(Collection collection){
        Collection newCollection = collectionDao.createCollection(collection);
        if(newCollection != null){
            return newCollection;
        }else{
            throw new ServiceException("Error creating recipe! Recipe was not created.");
        }
    }

    public void deleteCollection(int id){
        if(collectionDao.deleteCollectionById(id) != 0){
            collectionDao.deleteCollectionById(id);
        }else{
            throw new ServiceException("Error deleting collection! 0 rows affected.");
        }
    }
}
