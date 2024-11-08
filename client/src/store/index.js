import { createStore as _createStore } from 'vuex';
import axios from 'axios';

// Service & Helper imports
import RecipeService from '../services/RecipeService';
import CollectionService from '../services/CollectionService';
import CategoryService from '../services/CategoryService';
import ErrorHandler from '../helpers/ErrorHandler';

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      recipes: [],
      collections: [],
      categories: []
    },

    getters: {
      recipes(state){
        return state.recipes;
      },
      collections(state){
        return state.collections;
      },
      categories(state){
        return state.categories;
      }
    },

    mutations: {
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
      },
      SET_USER(state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
      },
      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        state.token = '';
        state.user = {};
        axios.defaults.headers.common = {};
      },
      SET_RECIPES(state, recipes){
        state.recipes = recipes;
      },
      SET_COLLECTIONS(state, collections){
        state.collections = collections;
      },
      SET_CATEGORIES(state, categories){
        state.categories = categories;
      },
      ADD_CATEGORY(state, category){
        state.categories.push(category);
      },
      ADD_COLLECTION(state, collection){
        state.collections.push(collection);
      },
      ADD_RECIPE(state, recipe){
        state.recipes.push(recipe);
      }
    },

    actions: {
      setRecipes({ commit }){
        RecipeService.list()
          .then(response => {
              commit('SET_RECIPES', response.data);
          })
          .catch(error => {
              ErrorHandler.handleError(error, 'fetching recipes');
          });
      },
      setCollections({ commit }){
        CollectionService.list()
          .then(response => {
              commit('SET_COLLECTIONS', response.data);
          })
          .catch(error => {
              ErrorHandler.handleError(error, 'fetching collections');
          });
      },
      setCategories({ commit }){
        CategoryService.list()
          .then(response => {
              commit('SET_CATEGORIES', response.data);
          })
          .catch(error => {
              ErrorHandler.handleError(error, 'fetching categories');
          });
      },
      addCategory({ commit }, newCategory){
        CategoryService.create(newCategory)
          .then(() => {
            commit('ADD_CATEGORY', newCategory);
          })
          .catch(error => {
            ErrorHandler.handleError(error, 'creating new categor');
          });
      },
      addCollection({ commit }, newCollection){
        CollectionService.createCollection(newCollection)
          .then(() => {
            commit('ADD_COLLECTION', newCollection);
          })
          .catch(error => {
            ErrorHandler.handleError(error, 'creating new collection');
          });
      },
      addRecipe({ commit }, newRecipe){
        RecipeService.create(newRecipe)
          .then(() => {
            commit('ADD_RECIPE', newRecipe)
          })
          .catch(error => {
            ErrorHandler.handleError(error, 'creating new recipe');
          });
      },
      deleteCategory({ dispatch }, categoryId){
        CategoryService.delete(categoryId)
          .then(() => {
            dispatch('setCategories');
            window.alert("Category deleted!");
          })
          .catch(error => {
            ErrorHandler.handleError(error, 'deleting category');
          });
      },
      deleteCollection({ dispatch }, collectionId){
        CollectionService.deleteCollection(collectionId)
          .then(() => {
              dispatch('setCollections');
              window.alert('Collection deleted!');
          })
          .catch(error => {
              ErrorHandler.handleError(error, 'deleting collection');
          })
      },
      deleteRecipe({ dispatch }, recipeId){
        RecipeService.delete(recipeId)
          .then(() => {
              dispatch('setRecipes');
              window.alert('Recipe deleted!');
          })
          .catch(error => {
              ErrorHandler.handleError(error, 'deleting recipe');
          })
      }
    }
  })
  return store;
}