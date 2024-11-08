<template>
    <Transition name="modal">
        <div v-if="show" class="modal-mask">
            <div class="modal-container">
                <div class="modal-header">
                <h3>Edit Recipe Details</h3>
                </div>
                <div class="modal-body">
                    <form>
                        <label for="name">Recipe Name:</label>
                        <br/>
                        <input type="text" name="name" v-model="recipe.name"/>
                        <br/><br/>
                        <label for="category">Category:</label>
                        <br/>
                        <select name="category" v-model="recipe.categoryId">
                            <option value="" disabled>--Select a Category--</option>
                            <option v-for="category in categories" :key="category.id" :value="category.id">
                                {{ category.name }}
                            </option>
                        </select>
                        <br/> <br/>
                        <label for="collection">Collection:</label>
                        <br/>
                        <select name="collection" v-model="recipe.collectionId">
                            <option value="" disabled>--Select a Collection--</option>
                            <option v-for="collection in collections" :key="collection.id" :value="collection.id">
                                {{ collection.name }}
                            </option>
                        </select>
                    </form>
                </div>      
                <div class="modal-footer">
                <button class="close" @click="$emit('close')">
                    Close
                </button>
                <button class="submit modal-default-button" @click="updateRecipe">
                    Submit
                </button>
                </div>
            </div>
        </div>
    </Transition>
</template>

<script>
import RecipeService from '../services/RecipeService';
import CategoryService from '../services/CategoryService';
import CollectionService from '../services/CollectionService';
import ErrorHandler from '../helpers/ErrorHandler';

export default{
    props: {
        show: Boolean
    },

    data(){
        return{
            recipe: {}
        }
    },

    computed: {
        categories(){
            return this.$store.state.categories;
        },

        collections(){
            return this.$store.state.collections;
        }
    },

    methods: {

        getCurrentRecipe(){
            RecipeService.get(this.$route.params.recipeId)
                .then(response => {
                    this.recipe = response.data;
                })
                .catch(error => {
                    ErrorHandler.handleError(error, 'getting current recipe');
                });
        },

        updateRecipe(){
            RecipeService.update(this.recipe.id, this.recipe)
                .then(() => {
                    window.alert("Recipe updated!");
                    this.$emit('close');
                    location.reload();
                })
                .catch(error => {
                    ErrorHandler.handleError(error, 'updating recipe');
                })
        }
    },

    created(){
        this.getCurrentRecipe();
        this.$store.dispatch('setCategories');
        this.$store.dispatch('setCollections');
    }
}

</script>