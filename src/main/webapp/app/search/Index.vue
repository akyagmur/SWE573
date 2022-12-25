<template>
  <div class="row mb-2">
    <div class="col-md-6" v-for="post in searchResult" :key="post.id">
      <SinglePost :post="post" />
    </div>
  </div>
</template>
<script>
import SinglePost from '@/home/SinglePost.vue';
export default {
  components: {
    SinglePost,
  },
  data() {
    return {
      query: this.$route.query.query,
      searchResult: [],
    };
  },
  mounted() {
    this.search();
  },
  methods: {
    search() {
      this.$http.get('/api/search', { params: { query: this.query } }).then(response => {
        this.searchResult = response.data;
      });
    },
  },
  watch: {
    $route(to, from) {
      this.query = to.query.query;
      this.search();
    },
  },
};
</script>
