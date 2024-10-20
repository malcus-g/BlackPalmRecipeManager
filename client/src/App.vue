<template>
  <div id="cart-app">
    <header>
      <img src="/header_logo.jpeg" alt="logo" @click="this.$router.push({name: 'home'})"/>
      <nav>
        <router-link :to="{name: 'home'}"> Home </router-link> 
        <router-link :to="{name: 'allRecipes'}">Recipes</router-link> 
        <router-link :to="{name: 'categories'}">Categories</router-link> 
        <router-link :to="{name: 'collections'}">Collections</router-link> 
        <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token">
          Logout
        </router-link>
        <router-link v-bind:to="{ name: 'login' }" v-else>Login</router-link>
      </nav>
    </header>
    
    <main>
      <router-view />
    </main>
    <footer>
      <p>&copy; 2024 The Black Palm Bakery. All rights reserved.</p>
    </footer>
  </div>
</template>

<style scoped>

  #cart-app {
    display: grid;
    grid-template-areas:
    "header"
    "main"
    "footer";
    grid-template-rows: auto 1fr auto; 
    grid-template-columns: 100%; 
    min-height: 100vh; 
  }

  header {
    grid-area: header;
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: auto;
    z-index: 9999; 
    background-color: #ffffff; 
    padding: 0 1em; 
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
  }

  main {
    grid-area: main;
    margin-top: 6rem;
    padding: 1rem;
  }

  footer {
    grid-area: footer;
    padding: .1rem;
    text-align: center;
  }

  header {
    display: flex;
    align-items: center; 
    justify-content: space-between; 
    padding: .5rem 1rem;
    background-color: white;
  }

  header img {
    max-width: 100px;
    border-radius: 5px; 
    cursor: pointer;
  }

  nav {
    display: flex;
    justify-content: flex-end;
    
  }

  nav a {
    text-decoration: none;
    color: #000000;
    margin: 0px 1em;
    padding: .4em 1em;
    border-radius: 8px;
    text-align: center;
  }

  nav a:hover {
    color: #bcb8b1; 
  }

  .router-link-active{
    color: #f4f3ee;
    background-color: rgb(224, 175, 160);
  }

</style>