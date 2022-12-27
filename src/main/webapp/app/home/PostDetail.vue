<template>
  <div class="row g-5">
    <div class="col-md-8">
      <h3 class="pb-4 mb-4 fst-italic border-bottom">From the Firehose</h3>

      <article class="blog-post">
        <h2 class="blog-post-title mb-1">{{ post.title }}</h2>
        <p class="blog-post-meta">{{ convertDateToHumanReadable(post.created_at) }} by {{ post.authorName }}</p>
        <!-- tags -->
        <div class="d-flex gap-2 mb-1 text-muted">
          <span v-for="tag in post.tags" :key="tag.id" class="badge bg-secondary" style="cursor: pointer" @click="goToTag(tag)">{{
            tag.name
          }}</span>
        </div>
        <!-- thumbnail -->
        <img width="200" class="bd-placeholder-img" v-if="post.image_url" v-bind:src="post.image_url" />
        <p>
          {{ post.content }}
        </p>
      </article>

      <!-- like/bookmark/comment -->
      <div class="d-flex gap-2 align-items-center justify-content-start p-4">
        <font-awesome-icon icon="fa-regular fa-bookmark" style="cursor: pointer" />
        <font-awesome-icon icon="fa-regular fa-heart" style="cursor: pointer" />
        <font-awesome-icon icon="fa-regular fa-comment" style="cursor: pointer" />
      </div>

      <!-- comments -->
      <div class="d-flex gap-2 align-items-center justify-content-start p-2 border border-1 rounded">
        <font-awesome-icon icon="fa-regular fa-user-circle" style="cursor: pointer" />
        <input v-model="comment" type="text" class="form-control" placeholder="Add a comment" v-on:keyup.enter="createComment" />
      </div>

      <div v-for="comment in post.comments" class="d-flex gap-2 align-items-center justify-content-start p-1">
        <font-awesome-icon icon="fa-regular fa-user-circle" style="cursor: pointer" />
        <div class="card">
          <div class="card-body">
            <div class="mb-1 text-muted">by {{ comment.user.login }} on {{ convertDateToHumanReadable(comment.created_at) }}</div>
            <p class="card-text">{{ comment.comment }}</p>
          </div>
        </div>
      </div>
    </div>

    <div class="col-md-4">
      <div class="position-sticky" style="top: 2rem">
        <div class="p-4 mb-3 bg-light rounded">
          <h4 class="fst-italic">About</h4>
          <p class="mb-0">
            Customize this section to tell your visitors a little bit about your publication, writers, content, or something else entirely.
            Totally up to you.
          </p>
        </div>

        <div class="p-4">
          <h4 class="fst-italic">Archives</h4>
          <ol class="list-unstyled mb-0">
            <li><a href="#">March 2021</a></li>
            <li><a href="#">February 2021</a></li>
            <li><a href="#">January 2021</a></li>
            <li><a href="#">December 2020</a></li>
            <li><a href="#">November 2020</a></li>
            <li><a href="#">October 2020</a></li>
            <li><a href="#">September 2020</a></li>
            <li><a href="#">August 2020</a></li>
            <li><a href="#">July 2020</a></li>
            <li><a href="#">June 2020</a></li>
            <li><a href="#">May 2020</a></li>
            <li><a href="#">April 2020</a></li>
          </ol>
        </div>

        <div class="p-4">
          <h4 class="fst-italic">Elsewhere</h4>
          <ol class="list-unstyled">
            <li><a href="#">GitHub</a></li>
            <li><a href="#">Twitter</a></li>
            <li><a href="#">Facebook</a></li>
          </ol>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      post: {},
      comment: '',
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
    convertDateToHumanReadable(date) {
      return new Date(date).toLocaleDateString('en-gb', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        timeZone: 'utc',
      });
    },
    createComment() {
      let data = {
        comment: this.comment,
        postId: this.post.id,
      };
      this.$http.post(`/api/comments`, data).then(response => {
        this.fetchPost();
        this.resetComment();
        this.$toast.success('Comment created successfully');
      });
    },
    resetComment() {
      this.comment = '';
    },
  },
};
</script>
