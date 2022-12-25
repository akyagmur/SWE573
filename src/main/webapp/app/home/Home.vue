<template>
  <div class="row mb-2" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="15">
    <div class="col-md-6" v-for="post in this.$store.getters.posts" :key="post.id">
      <SinglePost :post="post" />
    </div>
    <!--if busy show spinner-->
    <div class="col-md-12">
      <div v-if="busy" class="d-flex justify-content-center">
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
  components: {
    SinglePost,
  },
  data() {
    return {
      busy: false,
    };
  },
  mounted() {
    this.$store.commit('setPageNumber', 0);
  },
  methods: {
    fetchPosts() {
      if (this.$store.getters.posts.length === 0) {
        this.$store.dispatch('fetchPosts');
      }
    },
    loadMore() {
      if (this.$store.getters.fetchMore) {
        this.busy = true;
        setTimeout(() => {
          this.$store.dispatch('fetchPosts').then(() => {
            this.busy = false;
          });
        }, 1000);
      }
    },
    setPostToEdit(post) {
      this.$store.dispatch('setShowPostCreateModal', true).then(() => {
        console.log(post);
        this.$store.commit('setPostToEdit', post);
        this.$bootstrap.Modal.getOrCreateInstance(document.getElementById('newPostModal')).show();
      });
    },
  },
};
</script>
