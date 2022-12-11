<template>
  <div>
    <div class="container">
      <navigation></navigation>
    </div>
    <main class="container">
      <router-view></router-view>
    </main>
  </div>
</template>

<script>
import Navigation from '@/core/Navigation.vue';
export default {
  name: 'App',
  components: {
    navigation: Navigation,
  },
};
</script>
