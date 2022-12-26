<template></template>

<script>
export default {
  data() {
    return {
      post: {},
    };
  },
  mounted() {
    this.fetchPost();
  },
  methods: {
    fetchPost() {
      this.$http.get(`/api/posts/${this.$route.params.id}`).then(response => {
        this.post = response.data;
      });
    },
  },
};
</script>
