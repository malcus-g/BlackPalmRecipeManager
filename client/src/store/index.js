import { createStore as _createStore } from 'vuex';
import axios from 'axios';

// Service imports
import RecipeService from '../services/RecipeService';

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      recipes: []
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
      }
    },
    actions: {
      setRecipes({ commit }){
        RecipeService.list()
          .then(response => {
              commit('SET_RECIPES', response.data);
          })
          .catch(error => {
              console.error(error.response.data);
          });
      }
    }

  })
  return store;
}