<template>
    <section id="navigation-cards">
        <article class="navigation-card" v-for="navigation in navigationCards" :key="navigation.id"
                 @click="navigateToCollection(navigation.routeName)">
            <div class="name">{{ navigation.name }}</div>
        </article>
    </section>
</template>

<script>
    export default{
        props: ['navigationCards'],
        methods: {
            navigateToCollection(routeName){
                this.$router.push({name: `${routeName}`});
            }
        }
    }
</script>

<style scoped>
    #navigation-cards{
        display: flex;
        justify-content: center;
        flex-wrap: wrap;
    }

    .navigation-card{
        display: inline-block;
        width: 35rem;
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

    .navigation-card:hover{
        transform: scale(1.1);
        background-color: rgba(0,0,0, .20);
        cursor: pointer;
    }

    .name{
        color: #000000;
        font-size: 1.5rem;
        padding: 1em;
    }
</style>