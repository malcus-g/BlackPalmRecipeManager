import axios from 'axios';

export default{

    list(){
        return axios.get('/ingredients');
    },

    getById(ingredientId){
        return axios.get(`/ingredients/${ingredientId}`);
    },

    create(ingredient){
        return axios.post('/ingredients', ingredient);
    }
}