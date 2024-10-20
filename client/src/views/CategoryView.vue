<template>
    <div id="category-view">
        <div id="buttons">
            <font-awesome-icon class="fa-icon delete-category" :icon="['fa', 'square-minus']" @click="deleteCategory"/>    
        </div>
        <h1 class="view-title">{{ category.name }}</h1>
        <div id="main-content">
            <loading-spinner id="spinner" :spin="isLoading"/>
            <recipe-cards :recipes="recipes"></recipe-cards>
        </div>
    </div>
</template>

<script>
    import RecipeCards from '../components/RecipeCards.vue';
    import CategoryService from '../services/CategoryService.js';
    import LoadingSpinner from '../components/LoadingSpinner.vue';

    export default{
        components: {
            RecipeCards,
            LoadingSpinner
        },
        data(){
            return{
                isLoading: false,
                recipes: [],
                category: ''
            }
        },
        methods: {
            getRecipes(){
                this.isLoading = true;
                CategoryService.getCategoryRecipes(this.$route.params.categoryId)
                    .then(response => {
                        this.recipes = response.data;
                        this.isLoading = false;
                    })
                    .catch(error => {
                        console.error(error.response.data)
                    });
            },

            getCategory(){
                CategoryService.getCategoryById(this.$route.params.categoryId)
                    .then(response => {
                        this.category = response.data;
                    })
                    .catch(error => {
                        console.error(error.response.data)
                    });
            },

            deleteCategory(){
                if(window.confirm("Are you sure you want to delete this category?")){
                    CategoryService.delete(this.$route.params.categoryId)
                        .then(() => {
                            this.$router.push({name: "categories"});
                        })
                        .catch(error => {
                            //TODO error handling
                            console.error(error);
                        })
                }
            }
        },
        created(){
            this.getRecipes();
            this.getCategory();
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