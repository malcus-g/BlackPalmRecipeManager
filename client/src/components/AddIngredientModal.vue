<template>
    <Transition name="modal">
        <div v-if="show" class="modal-mask">
            <div class="modal-container">
                <div class="modal-header">
                <h3>Add an Ingredient</h3>
                </div>
                <div class="modal-body">
                    <form>
                        <label for="name">Name: </label>
                        <br/>
                        <input name="name" type="text" v-model.lazy.trim="ingredientToCreate.name" placeholder="Ingredient Name" required/>
                        <br/> <br/>
                        <label for="quantity">Quantity: </label>
                        <br/>
                        <input name="quantity" type="number" v-model="ingredientToAdd.quantity" required/>
                        <br/> <br/>
                        <label for="measurement-unit">Measurement Unit: </label>
                        <br/>
                        <input name="measurement-unit" type="text" v-model="ingredientToAdd.measurementUnit" placeholder="e.g.  g, oz, lb, ml, etc."/>
                    </form>
                </div>      
                <div class="modal-footer">
                <button class="close" @click="$emit('close')">
                    Close
                </button>
                <button class="submit modal-default-button" @click="addIngredient">
                    Submit
                </button>
                </div>
            </div>
        </div>
    </Transition>
</template>

<script>
import IngredientService from '../services/IngredientService';
import RecipeService from '../services/RecipeService';

export default{

    props: {
        show: Boolean,
    },

    data(){
        return{
            ingredientToCreate: {
                name: '',
                allergenId: 1,
                imagePath: ''
            },
            ingredientToAdd: {
                ingredientId: 0,
                recipeId: this.$route.params.recipeId,
                quantity: 0,
                measurementUnit: ''
            }
        }
    },

    methods: {
        addIngredient(){
            IngredientService.create(this.ingredientToCreate)
                .then(response => {
                    let createdIngredient = response.data;
                    this.ingredientToAdd.ingredientId = createdIngredient.id;
                    RecipeService.addIngredient(this.$route.params.recipeId, this.ingredientToAdd)
                        .then(() => {
                            window.alert("Ingredient added!");
                            this.$emit('close');
                            location.reload();
                        })
                        .catch(error => {
                            console.error(error);
                        });
                })
                .catch(error => {
                    console.error(error);
                });
            
        },
    }
}

</script>