import axios from 'axios';

export default{
    list(){
        return axios.get('/categories');
    },

    getCategoryById(categoryId){
        return axios.get(`/categories/${categoryId}`)
    },

    getCategoryRecipes(categoryId){
        return axios.get(`/categories/${categoryId}/recipes`);
    },

    create(category){
        return axios.post('/categories', category);
    },

    update(categoryId, category){
        return axios.put(`/categories/${categoryId}`, category);
    },

    delete(categoryId){
        return axios.delete(`/categories/${categoryId}`);
    }
}