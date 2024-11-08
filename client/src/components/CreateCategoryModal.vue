<template>
    <Transition name="modal">
      <div v-if="show" class="modal-mask">
        <div class="modal-container">
          <div class="modal-header">
            <h3>New Category</h3>
          </div>
          <div class="modal-body">
            <form>
              <input name="category-name" type="text" v-model.lazy.trim="newCategory.name" placeholder="Category Name" required/>
            </form>
          </div>      
          <div class="modal-footer">
            <button class="close" @click="$emit('close')">
              Close
            </button>
            <button class="submit modal-default-button" @click="createNewCategory">
              Submit
            </button>
          </div>
        </div>
      </div>
    </Transition>
  </template>

<script >
import CategoryService from '../services/CategoryService.js';
import ErrorHandler from '../helpers/ErrorHandler';

export default{
    props: {
        show: Boolean
    },
    data(){
      return{
        newCategory: {
          name: ''
        }
      }
    },
    methods: {
      createNewCategory(){
        if(this.newCategory.name){
          this.$store.dispatch('addCategory', this.newCategory);
          this.newCategory.name = '';
          this.$emit('close');
          // Should find a better way to handle refresh after the store action completes
          setTimeout(() => {
            location.reload();
          }, 100);
        }else{
          window.alert('New category must have a name!')
        }
      }
    }
}
</script> 

<style scoped>

</style>