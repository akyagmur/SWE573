<template>
  <div>
    <div class="container">
      <navigation></navigation>
    </div>
    <main class="container">
      <post-delete-modal />
      <router-view></router-view>
    </main>
  </div>
</template>

<script>
import Navigation from '@/core/Navigation.vue';
import PostDeleteModalVue from './post/PostDeleteModal.vue';
export default {
  name: 'App',
  components: {
    navigation: Navigation,
    'post-delete-modal': PostDeleteModalVue,
  },
};
</script>
