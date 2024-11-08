<template>
    <Transition name="modal">
        <div v-if="show" class="modal-mask">
            <div class="modal-container">
                <div class="modal-header">
                <h3>Add a Recipe</h3>
                </div>
                <div class="modal-body">
                    <select id="recipe" name="recipe" v-model="recipeToAdd" required>
                        <option value="" disabled>--Select a Recipe to Add--</option>
                        <option v-for="recipe in allRecipes" :key="recipe.id" :value="recipe">
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

export default{
    props: {
        show: Boolean
    },

    data(){
        return{
            recipeToAdd: Object
        }
    },
    computed: {
        allRecipes(){
            return this.$store.getters.recipes;
        }
    },
    methods: {
        updateCollection(){
            this.recipeToAdd.collectionId = this.$route.params.collectionId;
            RecipeService.update(this.recipeToAdd.id, this.recipeToAdd)
                .then(() => {
                    this.recipeToAdd = null;
                    this.$emit('close');
                    window.alert("Recipe added to collection!");
                    location.reload();
                })
                .catch(error => {
                    // Check if error response is available and show relevant message to user
                    if (error.response) {
                        // Server responded with a status code out of the range of 2xx
                        const status = error.response.status;
                        let errorMessage;

                        if (status === 400) {
                            errorMessage = "Invalid recipe data. Please check your inputs.";
                        } else if(status === 403){
                            errorMessage = "Access restricted; You must be logged in as an admin for this!";
                        } else if (status === 404) {
                            errorMessage = "Recipe not found. It may have been removed.";
                        } else if (status === 500) {
                            errorMessage = "Server error. Please try again later.";
                        } else {
                            errorMessage = "An error occurred. Please try again.";
                        }

                        window.alert(errorMessage);
                    } else if (error.request) {
                        // Request was made but no response was received
                        window.alert("Network error. Please check your connection.");
                    } else {
                        // Something else happened in setting up the request
                        window.alert("An unexpected error occurred. Please try again.");
                    }
                });
        }
    },

    created(){
        this.$store.dispatch('setRecipes');
    }
}
</script>