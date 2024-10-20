<template>
    <section id="recipe-cards">
        <article class="recipe-card" v-for="recipe in recipes" :key="recipe.id"
                 @click="navigateToRecipe(recipe.id)">
            <div class="recipe-image">
                <img class="cover" :src="'/' + recipe.imagePath"/>
            </div>
            <div class="name">{{ recipe.name }}</div>
        </article>
    </section>
</template>

<script>
    export default{
        props: ['recipes'],
        methods: {
            navigateToRecipe(id){
                this.$router.push({name: 'recipe', params: {recipeId: id}});
            }
        }
    }
</script>

<style scoped>
    #recipe-cards{
        display: flex;
        justify-content: center;
        flex-wrap: wrap;
    }

    .recipe-card{
        display: inline-block;
        width: 325px;
        height: 350px;
        margin: 30px;
        text-align: center;
        font-weight: bold;
        color: #0000;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
        overflow: hidden;
        transition: all .2s ease-in;
        border-radius: 15px;
    }

    .recipe-card:hover{
        transform: scale(1.05);
        background-color: rgba(0,0,0, .20);
        cursor: pointer;
    }

    .name{
        color: black;
        font-size: 1.2rem;
        padding: 2.5rem;
    }

    .cover{
        object-fit: cover;
        width: 100%;
        height: 225px;
    }

</style>