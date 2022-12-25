<template>
  <div class="row mb-2" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="15">
    <div class="col-md-6" v-for="post in posts" :key="post.id">
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
      posts: [],
    };
  },
  mounted() {
    this.getTags();
  },
  methods: {
    getTags() {
      this.$http.get('/api/tags/' + this.$router.currentRoute.params.tag).then(response => {
        this.posts = response.data.posts;
        console.log(response.data);
      });
    },
    loadMore() {
      this.$http.get('/api/tags/' + this.$router.currentRoute.params.tag).then(response => {
        this.posts = this.posts.concat(response.data);
      });
    },
  },
};
</script>
