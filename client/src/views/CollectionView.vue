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
            <loading-spinner id="spinner" :spin="isLoading"/>
            <recipe-cards :recipes="recipes"></recipe-cards>
        </div>
    </div>
</template>

<script>
import RecipeCards from '../components/RecipeCards.vue';
import CollectionService from '../services/CollectionService';
import LoadingSpinner from '../components/LoadingSpinner.vue';
import AddRecipeToCollectionModal from '../components/AddRecipeToCollectionModal.vue';
import RemoveRecipeFromCollectionModal from '../components/RemoveRecipeFromCollectionModal.vue';

export default{
    components: {
        RecipeCards,
        LoadingSpinner,
        AddRecipeToCollectionModal,
        RemoveRecipeFromCollectionModal
    },
    data(){
        return{
            isLoading: false,
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
                    //TODO error handling
                    console.error(error.response.data)
                });
        },

        getCollection(){
            CollectionService.getCollectionById(this.$route.params.collectionId)
                .then(response => {
                    this.collection = response.data;
                })
                .catch(error => {
                    //TODO error handling
                    console.error(error)
                });
        },

        deleteCollection(){
            if(window.confirm("Are you sure you want to delete this collection?")){
                CollectionService.deleteCollection(this.$route.params.collectionId)
                    .then(() => {
                        //TODO notify user of collection deletion
                        this.$router.push({name: "collections"})
                    })
                    .catch(error => {
                        //TODO error handling
                        console.error(error)
                    })
            }
        }
    },
    created(){
        this.getRecipes();
        this.getCollection();
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