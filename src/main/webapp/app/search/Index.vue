<template>
  <div class="row mb-2">
    <div class="col-md-6" v-for="post in searchResult" :key="post.id">
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
  </div>
</template>
<script>
export default {
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
