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
            recipeToAdd: Object,
            allRecipes: []
        }
    },

    methods: {
        getRecipes(){
            RecipeService.list()
                .then(response => {
                    this.allRecipes = response.data;
                })
                .catch(error => {
                    console.error(error.response.data)
                });
        },

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
                    console.error(error);
                })
        }
    },

    created(){
        this.getRecipes();
    }
}
</script>