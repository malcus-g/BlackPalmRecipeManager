<template>
    <Transition name="modal">
        <div v-if="show" class="modal-mask">
        <div class="modal-container">
            <div class="modal-header">
            <h3>New Recipe</h3>
            </div>
            <div class="modal-body">
            <form>
                <input name="recipe-name" type="text" v-model.lazy.trim="newRecipe.name" placeholder="Recipe Name" required/>
                <br/> <br/>
                <select id="category" name="category" v-model="newRecipe.categoryId" required>
                    <option value=0 disabled >--Choose a category (Required)--</option>
                    <option v-for="category in categories" :key="category.id" 
                            :value="category.id">
                        {{ category.name }}
                    </option>
                </select>
                <br/> <br/>
                <select id="collection" name="collection" v-model="newRecipe.collectionId" required>
                    <option value=0 disabled >--Choose a collection (Required)--</option>
                    <option v-for="collection in collections" :key="collection.id"
                            :value="collection.id">
                        {{ collection.name }}    
                    </option>
                </select>
            </form>
            </div>      
            <div class="modal-footer">
            <button class="close" @click="$emit('close')">
                Close
            </button>
            <button class="submit modal-default-button" @click="createNewRecipe">
                Submit
            </button>
            </div>
        </div>
        </div>
    </Transition>
</template>

<script>
import RecipeService from '../services/RecipeService';
import CollectionService from '../services/CollectionService';
import CategoryService from '../services/CategoryService';

export default{
    props: {
        show: Boolean
    },

    data(){
        return{
            newRecipe: {
                name: '',
                categoryId: 0,
                collectionId: 0,
                imagePath: ''
            },
            categories: [],
            collections: []
        }
    },

    methods: {
        getCollections(){
            CollectionService.list()
                .then(response => {
                    this.collections = response.data;
                })
                .catch(error => {
                    //TODO error handling
                    console.error(error);
                })
        },

        getCategories(){
            CategoryService.list()
                .then(response => {
                    this.categories = response.data;
                })
                .catch(error => {
                    //TODO handle errors
                    console.error(error);
                });
        },

        createNewRecipe(){
            RecipeService.create(this.newRecipe)
                .then(response => {
                    this.newRecipe.name = '';
                    this.newRecipe.categoryId = 0;
                    this.newRecipe.collectionId = 0;
                    this.newRecipe.imagePath = '';
                    this.$emit('close');
                    location.reload();
                })
        }
    },

    created(){
        this.getCollections();
        this.getCategories();
    }
}
</script>

<style scoped>

</style>
