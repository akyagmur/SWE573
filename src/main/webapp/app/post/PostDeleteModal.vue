<template>
  <div id="deletePostModal" class="modal fade" tabindex="-1" aria-labelledby="deletePostModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="deletePostModalLabel">Delete Post</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <p>Are you sure you want to delete this post?</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-danger" @click="deletePost()">Delete</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'PostDeleteModal',
  methods: {
    deletePost(post) {
      this.$store.dispatch('deletePost').then(() => {
        this.$bootstrap.Modal.getInstance(document.getElementById('deletePostModal')).hide();
        this.$toast.success('Post deleted successfully!');
        setTimeout(() => {
          this.$router.go();
        }, 1000);
      });
    },
  },
};
</script>
