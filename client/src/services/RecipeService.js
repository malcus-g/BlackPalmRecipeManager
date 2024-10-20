import axios from 'axios';

export default{
    list(){
        return axios.get('/recipes');
    },

    search(name){
        return axios.get(`/recipes/search?name=${name}`);
    },

    get(recipeId){
        return axios.get(`/recipes/${recipeId}`);
    },

    view(recipeId){
        return axios.get(`/recipes/${recipeId}/view`);
    },

    create(recipe){
        return axios.post('/recipes', recipe);
    },

    update(recipeId, recipe){
        return axios.put(`/recipes/${recipeId}`, recipe);
    },

    delete(recipeId){
        return axios.delete(`/recipes/${recipeId}`);
    },
    
    getIngredients(recipeId){
        return axios.get(`/recipes/${recipeId}/ingredients`);
    },

    addIngredient(recipeId, ingredient){
        return axios.post(`/recipes/${recipeId}/ingredients`, ingredient)
    },

    removeIngredient(recipeId, ingredientId){
        return axios.delete(`/recipes/${recipeId}/ingredients/${ingredientId}`);
    },

    getInstructions(recipeId){
        return axios.get(`/recipes/${recipeId}/instructions`);
    },

    addInstruction(recipeId, instruction){
        return axios.post(`/recipes/${recipeId}/instructions`, instruction);
    },

    removeInstruction(recipeId, stepNumber){
        return axios.delete(`/recipes/${recipeId}/instructions/${stepNumber}`);
    }
}