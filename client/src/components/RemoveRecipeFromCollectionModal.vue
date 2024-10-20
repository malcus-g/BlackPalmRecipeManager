<template>
    <Transition name="modal">
        <div v-if="show" class="modal-mask">
        <div class="modal-container">
            <div class="modal-header">
            <h3>Remove a Recipe</h3>
            </div>
            <div class="modal-body">
                <select id="recipe" name="recipe" v-model="recipeToRemove" required>
                    <option value="" disabled>--Select a Recipe to Remove--</option>
                    <option v-for="recipe in recipes" :key="recipe.id" :value="recipe">
                        {{ recipe.name }}
                    </option>
                </select>
            </div>      
            <div class="modal-footer">
            <button class="close" @click="$emit('close')">
                Close
            </button>
            <button class="submit modal-default-button" @click="updateCollection">
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

export default{
    props: {
        show: Boolean
    },

    data(){
        return{
            recipeToRemove: Object,
            recipes: []
        }
    },

    methods: {
        getRecipes(){
            CollectionService.getCollectionRecipes(this.$route.params.collectionId)
                .then(response => {
                    this.recipes = response.data;
                })
                .catch(error => {
                    //TODO error handling
                    console.error(error.response.data)
                });
        },

        updateCollection(){
            this.recipeToRemove.collectionId = 1;
            RecipeService.update(this.recipeToRemove.id, this.recipeToRemove)
                .then(() => {
                    this.recipeToRemove = null;
                    this.$emit('close');
                    window.alert("Recipe removed from collection!");
                    location.reload();
                })
                .catch(error => {
                    console.error(error);
                })
        }
    },

    created(){
        this.getRecipes();
    }
}
</script>