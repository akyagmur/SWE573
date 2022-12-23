<template>
  <div class="modal" id="newPostModal" tabindex="-1" aria-labelledby="newPostModal" aria-hidden="true" ref="modal">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="newPostModal">Create Post</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form>
            <!-- post title -->
            <div class="mb-3 row">
              <label for="title" class="col-sm-2 col-form-label">Title</label>
              <div class="col-sm-10">
                <input v-model="title" type="text" class="form-control" id="title" />
                <div
                  v-if="postCreateError && postErrors.title"
                  class="text-danger"
                  v-bind:class="{ 'd-block': postCreateError && postErrors.title, 'd-none': !postCreateError || !postErrors.title }"
                >
                  {{ postErrors.title }}
                </div>
              </div>
            </div>
            <!-- post content -->
            <div class="mb-3 row">
              <label for="content" class="col-sm-2 col-form-label">Content</label>
              <div class="col-sm-10">
                <textarea v-model="content" class="form-control" id="content" rows="3"></textarea>
                <div
                  v-if="postCreateError && postErrors.content"
                  class="text-danger"
                  v-bind:class="{ 'd-block': postCreateError && postErrors.content, 'd-none': !postCreateError || !postErrors.content }"
                >
                  {{ postErrors.content }}
                </div>
              </div>
            </div>
            <!-- post tags -->
            <div class="mb-3 row">
              <label for="tags" class="col-sm-2 col-form-label">Tags</label>
              <div class="col-sm-10">
                <multiselect
                  v-model="tags"
                  tag-placeholder="Add this as new tag"
                  placeholder="Search or add a tag"
                  label="name"
                  track-by="code"
                  :options="this.$store.getters.tags"
                  :multiple="true"
                  :taggable="true"
                  @tag="addTag"
                >
                </multiselect>
              </div>
            </div>
            <!-- post url -->
            <div class="mb-3 row">
              <label for="url" class="col-sm-2 col-form-label">URL</label>
              <div class="col-sm-10">
                <input v-model="url" type="text" class="form-control" id="url" />
                <div
                  v-if="postCreateError && postErrors.url"
                  class="text-danger"
                  v-bind:class="{ 'd-block': postCreateError && postErrors.url, 'd-none': !postCreateError || !postErrors.url }"
                >
                  {{ postErrors.url }}
                </div>
              </div>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" v-on:click="save">Save</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Multiselect from 'vue-multiselect';
export default {
  name: 'PostCreateModal',
  components: {
    Multiselect,
  },
  data() {
    return {
      title: '',
      content: '',
      tags: [],
      url: '',
      postCreateError: false,
      myModal: null,
      postErrors: {},
      tags: this.$store.getters.tags || [],
    };
  },
  methods: {
    save() {
      let data = {
        title: this.title,
        content: this.content,
        tags: this.tags,
        url: this.url,
      };
      this.$http.post('/api/posts', data).then(
        response => {
          this.$emit('post-created', response.data);
          this.$bootstrap.Modal.getInstance(this.$refs.modal).hide();
          this.$toast.success('Post created successfully!');
          this.postErrors = {};
          this.resetPostForm();
          this.$store.dispatch('fetchPosts');
        },
        error => {
          this.postErrors = error.response.data.fieldErrors.reduce((acc, fieldError) => {
            acc[fieldError.field] = fieldError.message;
            return acc;
          }, {});
          this.postCreateError = true;
          this.$toast.error('Failed to create post!');
        }
      );
    },
    resetPostForm() {
      this.title = '';
      this.content = '';
      this.tags = '';
      this.url = '';
      this.postCreateError = false;
    },
    addTag(newTag) {
      const tag = {
        name: newTag,
      };
      this.$http.post('/api/tags', tag).then(
        response => {
          this.tags.push(response.data);
        },
        error => {
          this.$toast.error('Failed to create tag!');
        }
      );
    },
  },
};
</script>
