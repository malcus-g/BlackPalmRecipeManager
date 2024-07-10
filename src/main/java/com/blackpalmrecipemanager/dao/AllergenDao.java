package com.blackpalmrecipemanager.dao;

import com.blackpalmrecipemanager.model.Allergen;

import java.util.List;

public interface AllergenDao {

    List<Allergen> getAllergens();
    Allergen getAllergenByName(String name);
    Allergen createAllergen(Allergen collection);
    Allergen updateAllergen(Allergen collection);
    int deleteAllergenById(int id);
}
