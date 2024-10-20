<template>
    <section id="collection-cards">
        <article class="collection-card" v-for="collection in collections" :key="collection.id"
                 @click="navigateToCollection(collection.id)">
            <div class="name">{{ collection.name }}</div>
        </article>
    </section>
</template>

<script>
    export default{
        props: ['collections'],
        methods: {
            navigateToCollection(id){
                this.$router.push({name: 'collection', params: {collectionId: id}});
            }
        }
    }
</script>

<style scoped>
    #collection-cards{
        display: flex;
        justify-content: center;
        flex-wrap: wrap;
    }

    .collection-card{
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

    .collection-card:hover{
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