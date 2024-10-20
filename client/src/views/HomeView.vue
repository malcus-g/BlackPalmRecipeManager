<template>
  <div class="home">
    <div id="heading-line">
      <h1 id="home-title">
        The Black Palm Bakery - Recipe Manager
      </h1>
      <div>
        <loading-spinner id="spinner" v-bind:spin="isLoading" />
      </div>
    </div> 
    <p id="intro-message">
      Welcome to The Black Palm Bakery Recipe Manager! This is your one-stop-shop for managing all of your delicious recipes. Click one of the cards below to get started!
    </p>
    <navigation-cards :navigationCards="navCards"/> 
  </div>
</template>

<script>
import LoadingSpinner from "../components/LoadingSpinner.vue";
import NavigationCards from "../components/NavigationCards.vue";

export default {
  components: {
    LoadingSpinner,
    NavigationCards
  },
  data() {
    return {
      isLoading: false,
      navCards: [
        {
          name: 'Recipes',
          routeName: 'allRecipes',
          id: 1
        },
        {
          name: 'Categories',
          routeName: 'categories',
          id: 2
        },
        {
          name: 'Collections',
          routeName: 'collections',
          id: 3
        }
      ]
    };
  },
  computed: {
    isLoggedIn() {
      return this.$store.state.token.length > 0;
    },
  },
};
</script>

<style scoped>

#home-title{
    display: flex;
    justify-content: center;
    font-size: 2rem;
    text-shadow: 1px 1px 2px rgb(77, 77, 77);
    padding: 1em;
    border-bottom: 1px solid black;
}

#spinner{
    display: flex;
    width: 2em;
    height: 2em;
}

#heading-line > div{
  display: flex;
  justify-content: center;
}

#intro-message{
  display: flex;
  text-align: center;
  justify-content: center;
  margin-bottom: 5rem;
}

</style>