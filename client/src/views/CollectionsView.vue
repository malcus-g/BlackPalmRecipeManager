<template>
    <div id="collections-view">
        <div id="buttons">
            <font-awesome-icon class="fa-icon create-collection" :icon="['fa', 'square-plus']" @click="showCreateModal = true;"/>
            <Teleport to="body">
                <create-collection-modal :show="showCreateModal" @close="showCreateModal = false"/>
            </Teleport>
        </div>
        <h1 class="view-title">Collections </h1>
        <div id="collection-cards">
            <loading-spinner v-if="isLoading" id="spinner" :spin="isLoading"/>
            <collection-cards v-else :collections="collections"/>
        </div>
    </div>
</template>

<script>
import CollectionCards from '../components/CollectionCards.vue';
import CollectionService from '../services/CollectionService.js';
import LoadingSpinner from '../components/LoadingSpinner.vue';
import CreateCollectionModal from '../components/CreateCollectionModal.vue';

export default{
    components: {
        CollectionCards,
        LoadingSpinner,
        CreateCollectionModal
    },
    data(){
        return{
            isLoading: false,
            showCreateModal: false
        }
    },
    computed: {
        collections(){
            return this.$store.getters.collections;
        }
    },
    created(){
        this.$store.dispatch('setCollections');
    }
}

</script>

<style scoped>

#collections-view{
    display: grid;
    grid-template-rows: .1fr .3fr 3fr;
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

#collection-cards{
    grid-area: content;
    display: flex;
    justify-content: center;
    align-items: center;
}

.create-collection{
    margin: 0 1rem;
    font-size: 2rem;
}



</style>