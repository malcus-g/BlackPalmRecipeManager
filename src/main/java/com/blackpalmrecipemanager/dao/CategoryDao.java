package com.blackpalmrecipemanager.dao;

import com.blackpalmrecipemanager.model.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> getCategories();
    Category getCategoryByName(String name);
    Category createCategory(Category category);
    Category updateCategory(Category category);
    int deleteCategoryById(int id);
}
