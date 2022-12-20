<template></template>
<script>
export default {
  data() {
    return {
      posts: [],
    };
  },
  mounted() {
    this.fetchPosts();
  },
  methods: {
    fetchPosts() {
      this.$http.get('/api/posts').then(response => {
        this.posts = response.data;
      });
    },
  },
};
</script>
