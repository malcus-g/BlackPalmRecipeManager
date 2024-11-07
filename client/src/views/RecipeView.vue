<template>
    <div id="recipe-view">
        <div id="buttons">
            <font-awesome-icon class="fa-icon delete-recipe" :icon="['fa', 'trash-can']" @click="deleteRecipe"/>
            <font-awesome-icon class="fa-icon edit-name" :icon="['fa', 'pencil']" @click="showRecipeDetailsModal = true;"/>
            <Teleport to="body">
                <edit-recipe-details-modal :show="showRecipeDetailsModal" @close="showRecipeDetailsModal = false;"/>
            </Teleport>
        </div>
        <h1 class="view-title">
            {{ recipe.name }}
            
        </h1>
        <article id="recipe">
            <div id="ingredient-list">
                <h2>
                    Ingredients 
                    <font-awesome-icon class="fa-icon add-icon" :icon="['fa', 'square-plus']" @click="showIngredientModal = true;"/>
                    <Teleport to="body">
                        <add-ingredient-modal :show="showIngredientModal" @close="showIngredientModal = false;"/>
                    </Teleport>
                </h2>
                <p class="ingredient" v-for="ingredient in ingredients" :key="ingredient.ingredientId">
                    <font-awesome-icon class="fa-icon delete-ingredient" :icon="['fas', 'trash-can']" @click="removeIngredient(ingredient.ingredientId)"/>
                    {{ ingredient.ingredientName }} : {{ ingredient.quantity }} {{ ingredient.measurementUnit }}
                </p>
            </div>
            <div id="instruction-list">
                <h2>
                    Instructions 
                    <font-awesome-icon class="fa-icon add-icon" :icon="['fa', 'square-plus']" @click="showInstructionModal = true;" />
                    <Teleport to="body">
                        <add-instruction-modal :show="showInstructionModal" @close="showInstructionModal = false;"/>
                    </Teleport>
                </h2>
                <p class="instruction" v-for="instruction in instructions" :key="instruction.stepNumber">
                    <font-awesome-icon class="fa-icon delete-instruction" :icon="['fas', 'trash-can']" @click="removeInstruction(instruction.stepNumber)"/>
                    {{ instruction.stepNumber + ")" }} {{ instruction.stepText }}
                </p>
            </div>
        </article>
    </div>
</template>

<script>
import RecipeService from '../services/RecipeService';
import AddIngredientModal from '../components/AddIngredientModal.vue';
import AddInstructionModal from '../components/AddInstructionModal.vue';
import EditRecipeDetailsModal from '../components/EditRecipeDetailsModal.vue';

export default{
    components: {
        AddIngredientModal,
        AddInstructionModal,
        EditRecipeDetailsModal
    },
    data(){
        return{
            recipe: {
                id: 0,
                name: '',
                collectionId: 0,
                categoryId: 0,
                imagePath: ''
            },
            ingredients: [],
            instructions: [],
            isLoading: false,
            showIngredientModal: false,
            showInstructionModal: false,
            showRecipeDetailsModal: false
        }
    },
    methods: {
        getRecipe(){
            this.isLoading = true;
            RecipeService.get(this.$route.params.recipeId)
                .then(response => {
                    this.recipe = response.data;
                    this.isLoading = false;
                })
                .catch(error => {
                    console.error(error)
                });
        },

        getIngredients(){
            this.isLoading = true;
            RecipeService.getIngredients(this.$route.params.recipeId)
                .then(response => {
                    this.ingredients = response.data;
                    this.isLoading = false;
                })
                .catch(error => {
                    console.error(error);
                });
        },

        getInstructions(){
            this.isLoading = true;
            RecipeService.getInstructions(this.$route.params.recipeId)
                .then(response => {
                    this.instructions = response.data;
                    this.isLoading = false;
                })
                .catch(error => {
                    console.error(error);
                })
        },

        deleteRecipe(){
            if(window.confirm("Are you sure you want to delete this recipe?")){
                RecipeService.delete(this.$route.params.recipeId)
                    .then(() => {
                        this.$router.push({name: 'allRecipes'});
                    })
                    .catch(error => {
                        //TODO error handling
                        console.error(error);
                    })
            }
        },

        removeIngredient(ingredientId){
            if(window.confirm("Remove this ingredient from the recipe?")){
                this.isLoading = true;
                RecipeService.removeIngredient(this.$route.params.recipeId, ingredientId)
                    .then(() => {
                        this.isLoading = false;
                        location.reload();
                    })
                    .catch(error => {
                        console.error(error);
                    });
            }
        },

        removeInstruction(stepNumber){
            if(window.confirm("Remove this instruction from the recipe?")){
                this.isLoading = true;
                RecipeService.removeInstruction(this.$route.params.recipeId, stepNumber)
                    .then(() => {
                        this.isLoading = false;
                        location.reload();
                    })
                    .catch(error => {
                        console.error(error);
                    })
            }
        }
    },
    created(){
        this.getRecipe();
        this.getIngredients();
        this.getInstructions();
    }
}

</script>

<style scoped>

#recipe-view{
    display: grid;
    grid-template-rows: .1fr .3fr 4fr;
    grid-template-areas: 
        "buttons"
        "title"
        "content";
    justify-content: center;
}

#buttons{
    display: flex;
    grid-area: buttons;
    justify-content: left;
    width: 100%;
    margin: 1.5rem 0 0 0;
}

#recipe{
    grid-area: content;
    display: grid;
    grid-template-columns: 1fr 2fr; 
    gap: 1.5rem;
    height: fit-content; 
}

#ingredient-list, #instruction-list{
    padding: .5rem;
    
}

#ingredient-list{
    border-right: 1px solid #ccc; 
}

p{
    margin: .5rem 0;
    padding: 1rem;
    border-radius: 10px;
}

p:hover{
    background-color: rgb(224, 175, 160, .4);
}

h2{
    font-size: 1.5rem;
}

.add-icon{
    margin: 0 1.5rem;
    font-size: x-large;
    color:#000000
}

.delete-ingredient, .delete-instruction{
    float: right;
    margin: 0 0 0 2rem;
    color: #000000;
}

.edit-name, .delete-recipe{
    margin: 0 2rem;
    font-size: xx-large;
}



</style>