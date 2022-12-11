<template>
  <div>
    <header class="blog-header lh-1 py-3">
      <div class="row flex-nowrap justify-content-between align-items-center">
        <div class="col-4 pt-1">
          <router-link class="link-secondary" to="/">Home</router-link>
        </div>
        <div class="col-4 text-center">
          <a class="blog-header-logo text-dark" href="#">Short</a>
        </div>
        <div class="col-2 d-flex justify-content-end align-items-center">
          <input class="form-control form-control-dark" type="text" placeholder="Search" aria-label="Search" />
        </div>
        <div class="col-2 d-flex justify-content-end align-items-center">
          <a class="link-secondary" href="#" aria-label="Search">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="20"
              height="20"
              fill="none"
              stroke="currentColor"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              class="mx-3"
              role="img"
              viewBox="0 0 24 24"
            >
              <title>Search</title>
              <circle cx="10.5" cy="10.5" r="7.5"></circle>
              <path d="M21 21l-5.2-5.2"></path>
            </svg>
          </a>
          <router-link class="btn btn-sm btn-outline-secondary" href="#" v-if="!this.$store.getters.authenticated" to="/register"
            >Register</router-link
          >
          <button
            v-if="!this.$store.getters.authenticated"
            class="btn btn-sm btn-outline-secondary ms-2"
            data-bs-toggle="modal"
            data-bs-target="#loginModal"
          >
            Login
          </button>
          <button v-if="this.$store.getters.authenticated" @click="$store.dispatch('logout')" class="btn btn-sm btn-outline-secondary ms-2">
            Logout
          </button>
          <login-modal />
        </div>
      </div>
    </header>
    <div class="nav-scroller py-1 mb-2">
      <nav class="nav d-flex justify-content-between">
        <a class="p-2 link-secondary" href="#">World</a>
        <a class="p-2 link-secondary" href="#">U.S.</a>
        <a class="p-2 link-secondary" href="#">Technology</a>
        <a class="p-2 link-secondary" href="#">Design</a>
        <a class="p-2 link-secondary" href="#">Culture</a>
        <a class="p-2 link-secondary" href="#">Business</a>
        <a class="p-2 link-secondary" href="#">Politics</a>
        <a class="p-2 link-secondary" href="#">Opinion</a>
        <a class="p-2 link-secondary" href="#">Science</a>
        <a class="p-2 link-secondary" href="#">Health</a>
        <a class="p-2 link-secondary" href="#">Style</a>
        <a class="p-2 link-secondary" href="#">Travel</a>
      </nav>
    </div>
  </div>
</template>
<script>
/* import login modal */
import Login from '@/account/login/Login.vue';
export default {
  name: 'Navigation',
  components: {
    'login-modal': Login,
  },
};
</script>
