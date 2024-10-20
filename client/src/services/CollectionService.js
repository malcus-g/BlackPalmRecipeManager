import axios from 'axios';

export default{

    list(){
        return axios.get("/collections");
    },

    getCollectionById(collectionId){
        return axios.get(`/collections/${collectionId}`);
    },

    getCollectionRecipes(collectionId){
        return axios.get(`/collections/${collectionId}/recipes`);
    },

    createCollection(collection){
        return axios.post('/collections', collection);
    },

    deleteCollection(collectionId){
        return axios.delete(`/collections/${collectionId}`);
    }
}