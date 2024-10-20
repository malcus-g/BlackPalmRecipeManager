<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask">
      <div class="modal-container">
        <div class="modal-header">
          <h3>New Collection</h3>
        </div>
        <div class="modal-body">
          <form>
            <input name="collection-name" type="text" v-model.lazy.trim="newCollection.name" placeholder="Collection Name" required/>
          </form>
        </div>      
        <div class="modal-footer">
          <button class="close" @click="$emit('close')">
            Close
          </button>
          <button class="submit modal-default-button" @click="createNewCollection">
            Submit
          </button>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script >
import CollectionService from '../services/CollectionService';

export default{
    props: {
        show: Boolean
    },
    data(){
      return{
        newCollection: {
          name: ''
        }
      }
    },
    methods: {
      createNewCollection(){
        if(this.newCollection.name){
          CollectionService.createCollection(this.newCollection)
            .then(response => {
              this.newCollection.name = '';
              this.$emit('close');
              location.reload();
            })
            .catch(error => {
              console.error(error);
            });
        }
      }
    }
}
</script> 

<style scoped>

</style>