<template>
  <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
    <div class="col p-4 d-flex flex-column position-static">
      <h6 class="mb-0 fw-bold">{{ post.title }}</h6>
      <div class="mb-1 text-muted">
        {{ convertDateToHumanReadable(post.created_at) }} by
        <router-link :to="'/posts/by-user/' + post.authorLogin">{{ post.authorLogin }}</router-link>
      </div>
      <!-- truncate post content -->
      <p class="mb-auto">
        {{ truncateString(post.content, 100) }}
      </p>

      <!-- tags -->
      <div class="d-flex gap-2 mb-1 text-muted">
        <span v-for="tag in post.tags" :key="tag.id" class="badge bg-secondary" style="cursor: pointer" @click="goToTag(tag)">{{
          tag.name
        }}</span>
      </div>
      <div class="d-flex gap-2 align-items-center justify-content-start">
        <a @click="goToPostDetail(post)" style="cursor: pointer" class="text-primary">Continue reading</a> |
        <a :href="post.url" target="_blank">Go to original URL</a> |
        <font-awesome-icon icon="fa-regular fa-bookmark" style="cursor: pointer" />
        <font-awesome-icon icon="fa-regular fa-heart" style="cursor: pointer" />
      </div>
      <div class="d-flex gap-3 pt-2" v-if="isUsersPost(post.created_by)">
        <font-awesome-icon icon="fa-regular fa-pen-to-square" style="cursor: pointer" @click="setPostToEdit(post)" />
        <font-awesome-icon icon="fa-solid fa-trash" style="cursor: pointer" class="text-danger" />
      </div>
    </div>
    <div class="col-auto d-none d-lg-block align-self-center">
      <img width="200" class="bd-placeholder-img" v-if="post.image_url" v-bind:src="post.image_url" />
    </div>
  </div>
</template>
<script>
export default {
  props: {
    post: {
      type: Object,
      required: true,
    },
  },
  methods: {
    setPostToEdit(post) {
      this.$store.dispatch('setShowPostCreateModal', true).then(() => {
        this.$store.commit('setPostToEdit', post);
        this.$bootstrap.Modal.getOrCreateInstance(document.getElementById('newPostModal')).show();
      });
    },
    goToTag(tag) {
      this.$router.push({ name: 'Tag', params: { tag: tag.name } });
    },
    goToPostDetail(post) {
      this.$router.push({ name: 'PostDetail', params: { id: post.id } });
    },
  },
};
</script>
