package com.blackpalmrecipemanager.dao;

import com.blackpalmrecipemanager.model.Collection;

import java.util.List;

public interface CollectionDao {

    List<Collection> getCollections();
    Collection getCollectionByName(String name);
    Collection createCollection(Collection collection);
    Collection updateCollection(Collection collection);
    int deleteCollectionById(int id);

}
