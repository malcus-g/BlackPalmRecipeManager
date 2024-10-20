<template>
    <section id="category-cards">
        <article class="category-card" v-for="category in categories" :key="category.id"
                 @click="navigateToCategory(category.id)">
            <div class="name">{{ category.name }}</div>
        </article>
    </section>
</template>

<script>
    export default{
        props: ['categories'],
        methods: {
            navigateToCategory(id){
                this.$router.push({name: 'category', params: {categoryId: id}});
            }
        }
    }
</script>

<style scoped>
    #category-cards{
        display: flex;
        justify-content: center;
        flex-wrap: wrap;
    }

    .category-card{
        display: inline-block;
        width: 25rem;
        height: 5rem;
        margin: 30px;
        text-align: center;
        font-weight: bold;
        color: #f4f3ee;
        background-color: rgba(255, 255, 255, 0.5);
        box-shadow: 0 4px 7px rgba(0, 0, 0, 0.2);
        overflow: hidden;
        transition: all .2s ease-in;
        border-radius: 10px;
    }

    .category-card:hover{
        transform: scale(1.08);
        background-color: rgba(0,0,0, .20);
        cursor: pointer;
    }

    .name{
        color: #000000;
        font-size: 1.5rem;
        padding: 1em;
    }
</style>