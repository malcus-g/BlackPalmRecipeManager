<template>
    <div id="all-recipes-view">
        <div id="buttons">
            <font-awesome-icon class="fa-icon create-recipe" :icon="['fa', 'square-plus']" @click="showModal = true;"/>
            <Teleport to="body">
                <create-recipe-modal :show="showModal" @close="showModal = false;"/>
            </Teleport>
        </div>
        <h1 class="view-title">Recipes</h1>
        <div id="recipe-cards">
            <loading-spinner id="spinner" :spin="isLoading" v-if="isLoading"/>
            <recipe-cards :recipes="recipes" v-else></recipe-cards>
        </div>
    </div>
</template>

<script>
    import RecipeCards from '../components/RecipeCards.vue';
    import RecipeService from '../services/RecipeService.js';
    import LoadingSpinner from '../components/LoadingSpinner.vue';
    import CreateRecipeModal from '../components/CreateRecipeModal.vue';

    export default{
        components: {
            RecipeCards,
            LoadingSpinner,
            CreateRecipeModal
        },
        data(){
            return{
                isLoading: true,
                showModal: false
            }
        },
        computed: {
            recipes(){
                return this.$store.getters.recipes;
            }
        },
        methods: {
            loadRecipes(){
                this.$store.dispatch('setRecipes');
                this.isLoading = false;
            }
        },
        created(){
           this.loadRecipes();
        }
    }
</script>

<style scoped>

#all-recipes-view{
    display: grid;
    grid-template-rows: .1fr .3fr 5fr;
    grid-template-areas: 
        "buttons"
        "title"
        "content";
    justify-content: center;
}

#buttons{
    display: flex;
    grid-area: buttons;
    justify-content: start;
    width: 100%;
}

#recipe-cards{
    grid-area: content;
    display: flex;
    justify-content: center;
    align-items: center;
}

.create-recipe{
    margin: 0 1rem;
    font-size: 2rem;
}

</style>