package com.techelevator.service;

import com.techelevator.dao.CategoryDao;
import com.techelevator.exception.ServiceException;
import com.techelevator.model.Category;
import com.techelevator.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao){
        this.categoryDao = categoryDao;
    }

    public List<Category> getCategories(){
        return categoryDao.getCategories();
    }

    public Category getCategoryById(int id){
        return categoryDao.getCategoryById(id);
    }

    public List<Recipe> getCategoryRecipes(int id){
        return categoryDao.getCategoryRecipes(id);
    }

    public Category createCategory(Category category){
        Category newCategory = categoryDao.createCategory(category);
        if(newCategory == null){
            throw new ServiceException("Error creating new category!");
        }else{
            return newCategory;
        }
    }

    public Category updateCategory(Category category){
        if(categoryDao.updateCategory(category).equals(category)) {
            return categoryDao.updateCategory(category);
        }else{
            throw new ServiceException("Error updating category!");
        }
    }

    public void deleteCategory(int id){
        if(categoryDao.deleteCategoryById(id) != 0){
            categoryDao.deleteCategoryById(id);
        }else{
            throw new ServiceException("Error deleting category! 0 rows affected");
        }
    }

}
