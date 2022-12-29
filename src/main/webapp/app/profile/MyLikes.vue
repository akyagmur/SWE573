<template>
  <div class="row mb-2" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="15">
    <div class="col-md-6" v-for="post in posts" :key="post.id">
      <SinglePost :post="post" />
    </div>
    <!--if busy show spinner-->
    <div class="col-md-12">
      <div v-if="isBusy" class="d-flex justify-content-center">
        <div class="spinner-border" role="status">
          <span class="visually-hidden">Loading...</span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import SinglePost from '@/home/SinglePost.vue';
export default {
  name: 'MyPosts',
  components: {
    SinglePost,
  },
  data() {
    return {
      posts: [],
      isBusy: false,
    };
  },
  created() {},
  mounted() {},
  methods: {
    loadMore() {
      this.isBusy = true;
      this.$http.post('/api/profile/my-liked-posts').then(response => {
        this.posts = response.data;
        this.isBusy = false;
      });
    },
  },
};
</script>
