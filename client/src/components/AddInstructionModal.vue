<template>
    <Transition name="modal">
        <div v-if="show" class="modal-mask">
            <div class="modal-container">
                <div class="modal-header">
                <h3>Add an Instruction</h3>
                </div>
                <div class="modal-body">
                    <form>
                        <label for="step-number">Step Number: </label>
                        <br/>
                        <input name="step-number" type="number" v-model="instructionToAdd.stepNumber" required/>
                        <br/> <br/>
                        <label for="step-text">Instruction: </label>
                        <br/>
                        <textarea name="step-text" type="text" rows="2" cols="19" v-model="instructionToAdd.stepText"></textarea>
                    </form>
                </div>      
                <div class="modal-footer">
                <button class="close" @click="$emit('close')">
                    Close
                </button>
                <button class="submit modal-default-button" @click="addInstruction">
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
            instructionToAdd: {
                recipeId: this.$route.params.recipeId,
                stepNumber: 0,
                stepText: ''
            }
        }
    },

    methods: {
        addInstruction(){
            RecipeService.addInstruction(this.$route.params.recipeId, this.instructionToAdd)
                .then(() => {
                    window.alert("Instruction added!");
                    this.$emit('close');
                    location.reload();
                })
                .catch(error => {
                    console.error(error);
                });
        }
    }
}

</script>