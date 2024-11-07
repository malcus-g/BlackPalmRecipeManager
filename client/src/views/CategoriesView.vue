<template>
    <div id="categories-view">
        <div id="buttons">
            <font-awesome-icon class="fa-icon create-category" :icon="['fa', 'square-plus']" @click="showModal = true;"/>
            <Teleport to="body">
                <create-category-modal :show="showModal" @close="showModal = false"/>
            </Teleport>
        </div>
        <h1 class="view-title">Categories</h1>
        <div id="category-cards">
            <loading-spinner v-if="isLoading" id="spinner" :spin="isLoading"/>
            <category-cards v-else :categories="categories"/>
        </div>
    </div>
</template>

<script>
    import CategoryCards from '../components/CategoryCards.vue';
    import CategoryService from '../services/CategoryService.js';
    import LoadingSpinner from '../components/LoadingSpinner.vue';
    import CreateCategoryModal from '../components/CreateCategoryModal.vue';

    export default{
        components: {
            CategoryCards,
            LoadingSpinner,
            CreateCategoryModal
        },
        data(){
            return{
                isLoading: false,
                showModal: false
            }
        },
        computed: {
            categories(){
                return this.$store.getters.categories;
            }
        },
        created(){
            this.$store.dispatch('setCategories');
        }
    }
</script>

<style scoped>

#categories-view{
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
}

#category-cards{
    grid-area: content;
    display: flex;
    justify-content: center;
    align-items: center;
}

.create-category{
    margin: 0 1rem;
    font-size: 2rem;
}


</style>