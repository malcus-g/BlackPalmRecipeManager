<template>
    <div id="collection-view">
        <div id="buttons">
            <font-awesome-icon class="fa-icon collection-icon" :icon="['fa', 'trash-can']" @click="deleteCollection"/>
            <font-awesome-icon class="fa-icon collection-icon" :icon="['fa', 'square-minus']" @click="showRemoveModal = true;"/>
            <Teleport to="body">
                <remove-recipe-from-collection-modal :show="showRemoveModal" @close="showRemoveModal = false"/>
            </Teleport>
            <font-awesome-icon class="fa-icon collection-icon" :icon="['fa', 'square-plus']" @click="showAddModal = true;"/>
            <Teleport to="body">
                <add-recipe-to-collection-modal :show="showAddModal" @close="showAddModal = false"/>
            </Teleport>
        </div>
        <h1 class="view-title">{{ collection.name }}</h1>
        <div id="main-content">
            <loading-spinner v-if="isLoading" id="spinner" :spin="isLoading"/>
            <recipe-cards v-else :recipes="recipes"></recipe-cards>
        </div>
    </div>
</template>

<script>
import RecipeCards from '../components/RecipeCards.vue';
import CollectionService from '../services/CollectionService';
import LoadingSpinner from '../components/LoadingSpinner.vue';
import AddRecipeToCollectionModal from '../components/AddRecipeToCollectionModal.vue';
import RemoveRecipeFromCollectionModal from '../components/RemoveRecipeFromCollectionModal.vue';
import ErrorHandler from '../helpers/ErrorHandler';

export default{
    components: {
        RecipeCards,
        LoadingSpinner,
        AddRecipeToCollectionModal,
        RemoveRecipeFromCollectionModal
    },
    data(){
        return{
            isLoading: true,
            recipes: [],
            collection: '',
            showAddModal: false,
            showRemoveModal: false
        }
    },
    methods: {
        getRecipes(){
            this.isLoading = true;
            CollectionService.getCollectionRecipes(this.$route.params.collectionId)
                .then(response => {
                    this.recipes = response.data;
                    this.isLoading = false;
                })
                .catch(error => {
                    ErrorHandler.handleError(error, 'getting recipes in collection');
                });
        },

        getCollection(){
            CollectionService.getCollectionById(this.$route.params.collectionId)
                .then(response => {
                    this.collection = response.data;
                })
                .catch(error => {
                    ErrorHandler.handleError(error, 'getting the collection');
                });
        },

        deleteCollection(){
            if(window.confirm('Are you sure you want to delete this collection?')){
                this.$store.dispatch('deleteCollection', this.$route.params.collectionId);
                setTimeout(() => {
                    this.$router.push({name: 'collections'});
                }, 200);
            }
        }
    },
    created(){
        this.getCollection();
        this.getRecipes();
    }
}
</script>

<style >

#collection-view{
    display: grid;
    grid-template-rows: .3fr .5fr 2fr;
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
    margin-top: 1rem;
}

#main-content{
    grid-area: content;
    display: flex;
    justify-content: center;
}

.collection-icon{
    margin: 0 1rem;
    font-size: 2rem;
}

</style>