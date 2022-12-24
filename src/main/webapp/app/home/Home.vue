<template>
  <div class="row mb-2" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="15">
    <div class="col-md-6" v-for="post in this.$store.getters.posts" :key="post.id">
      <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
        <div class="col p-4 d-flex flex-column position-static">
          <strong class="d-inline-block mb-2 text-success">Design</strong>
          <h3 class="mb-0">{{ post.title }}</h3>
          <div class="mb-1 text-muted">
            {{ convertDateToHumanReadable(post.created_at) }}
          </div>
          <p class="mb-auto">{{ post.content }}</p>

          <!-- tags -->
          <div class="mb-1 text-muted">
            <span v-for="tag in post.tags" :key="tag.id" class="badge bg-secondary">{{ tag.name }}</span>
          </div>
          <a :href="post.url" class="stretched-link">Continue reading</a>
        </div>
        <div class="col-auto d-none d-lg-block">
          <svg
            class="bd-placeholder-img"
            width="200"
            height="250"
            xmlns="http://www.w3.org/2000/svg"
            role="img"
            aria-label="Placeholder: Thumbnail"
            preserveAspectRatio="xMidYMid slice"
            focusable="false"
          >
            <title>Placeholder</title>
            <rect width="100%" height="100%" fill="#55595c"></rect>
            <text x="50%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text>
          </svg>
        </div>
      </div>
    </div>

    <!-- if busy show spinner-->
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
export default {
  data() {
    return {
      busy: false,
    };
  },
  mounted() {
    this.fetchTags();
    this.$store.commit('setPageNumber', 0);
  },
  methods: {
    fetchPosts() {
      if (this.$store.getters.posts.length === 0) {
        this.$store.dispatch('fetchPosts');
      }
    },
    fetchTags(post) {
      if (this.$store.getters.tags.length === 0) {
        this.$store.dispatch('fetchTags');
      }
    },
    loadMore() {
      this.busy = true;
      setTimeout(() => {
        this.$store.dispatch('fetchPosts').then(() => {
          this.busy = false;
        });
      }, 3000);
    },
  },
};
</script>
