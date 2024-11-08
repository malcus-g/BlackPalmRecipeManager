<template>
    <div id="category-view">
        <div id="buttons">
            <font-awesome-icon class="fa-icon delete-category" :icon="['fa', 'trash-can']" @click="deleteCategory"/>    
        </div>
        <h1 class="view-title">{{ category.name }}</h1>
        <div id="main-content">
            <loading-spinner id="spinner" :spin="isLoading" v-if="isLoading"/>
            <recipe-cards :recipes="recipes" v-else></recipe-cards>
        </div>
    </div>
</template>

<script>
    import RecipeCards from '../components/RecipeCards.vue';
    import CategoryService from '../services/CategoryService.js';
    import LoadingSpinner from '../components/LoadingSpinner.vue';
    import ErrorHandler from '../helpers/ErrorHandler';

    export default{
        components: {
            RecipeCards,
            LoadingSpinner
        },
        data(){
            return{
                isLoading: true,
                recipes: [],
                category: ''
            }
        },
        methods: {
            getRecipes(){
                CategoryService.getCategoryRecipes(this.$route.params.categoryId)
                    .then(response => {
                        this.recipes = response.data;
                        this.isLoading = false;
                    })
                    .catch(error => {
                        ErrorHandler.handleError(error, 'getting recipes in category');
                    });
            },

            getCategory(){
                CategoryService.getCategoryById(this.$route.params.categoryId)
                    .then(response => {
                        this.category = response.data;
                    })
                    .catch(error => {
                        ErrorHandler.handleError(error, 'getting the category');
                    });
            },

            deleteCategory(){
                if(window.confirm("Are you sure you want to delete this category?")){
                    this.$store.dispatch('deleteCategory', this.$route.params.categoryId);
                    setTimeout(() => {
                        this.$router.push({name: "categories"});
                    }, 200)
                }
            }
        },
        created(){
            this.getCategory();
            this.getRecipes();
        }
    }
</script>

<style scoped>


#category-view{
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
}

#main-content{
    grid-area: content;
    display: flex;
    justify-content: center;
}

.delete-category{
    margin: 0 1rem;
    font-size: 2rem;
}

</style>