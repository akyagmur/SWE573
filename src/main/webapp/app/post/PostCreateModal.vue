<template>
  <div class="modal" id="newPostModal" tabindex="-1" aria-labelledby="newPostModal" aria-hidden="true" ref="modal">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="newPostModal">{{ editMode ? 'Edit' : 'Create' }} Post</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form>
            <!-- thumbnail of url -->
            <div class="mb-3 row" v-if="image_url">
              <label for="thumbnail" class="col-sm-2 col-form-label">Thumbnail</label>
              <div class="col-sm-10">
                <img v-if="image_url" v-bind:src="image_url" class="img-thumbnail" />
                <div v-else class="img-thumbnail"></div>
              </div>
            </div>
            <!-- post url -->
            <div class="mb-3 row">
              <label for="url" class="col-sm-2 col-form-label">URL</label>
              <div class="col-sm-10">
                <input v-model="url" name="url" type="text" class="form-control" id="url" />
                <div
                  v-if="postCreateError && postErrors.url"
                  class="text-danger"
                  v-bind:class="{ 'd-block': postCreateError && postErrors.url, 'd-none': !postCreateError || !postErrors.url }"
                >
                  {{ postErrors.url }}
                </div>
              </div>
              <!-- fetching details -->
              <div v-if="isBusy" class="d-flex justify-content-center align-items-center p-2">
                <div class="spinner-border text-primary" role="status">
                  <span class="visually-hidden">Loading...</span>
                </div>
                <span class="p-2 text-primary">Fetching url details...</span>
              </div>
            </div>
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
                  v-model="selectedTags"
                  tag-placeholder="Add this as new tag"
                  placeholder="Search or add a tag"
                  label="name"
                  track-by="id"
                  :options="this.$store.getters.tags"
                  :multiple="true"
                  :taggable="true"
                  @tag="addTag"
                >
                </multiselect>
              </div>
            </div>
            <!-- is private checkbox -->
            <div class="mb-3 row">
              <div class="col-sm-10 offset-sm-2">
                <div class="form-check form-switch">
                  <input class="form-check-input" type="checkbox" id="isPrivate" v-model="is_private" />
                  <label class="form-check label" for="isPrivate">Private</label>
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
      post_id: null,
      title: '',
      content: '',
      selectedTags: [],
      url: '',
      image_url: '',
      is_private: false,
      postCreateError: false,
      myModal: null,
      postErrors: {},
      isBusy: false,
      editMode: false,
    };
  },
  mounted() {
    this.fetchTags();
  },
  methods: {
    save() {
      let data = {
        id: this.post_id,
        title: this.title,
        content: this.content,
        tags: this.selectedTags || [],
        url: this.url,
        image_url: this.image_url,
        is_private: this.is_private,
      };
      let method = 'post';
      let url = '/api/posts';
      let action = 'create';
      if (this.editMode) {
        method = 'patch';
        url = `/api/posts/${this.post_id}`;
        action = 'update';
      }
      this.$http[method](url, data).then(
        response => {
          this.$emit('post-created', response.data);
          this.$bootstrap.Modal.getInstance(this.$refs.modal).hide();
          this.$toast.success(`Post ${action} successfully!`);
          this.postErrors = {};
          this.resetPostForm();
          this.$store.commit('setPostToEdit', null);
          this.$store.dispatch('fetchPosts');
        },
        error => {
          this.postErrors = error.response.data.fieldErrors.reduce((acc, fieldError) => {
            acc[fieldError.field] = fieldError.message;
            return acc;
          }, {});
          this.postCreateError = true;
          this.$toast.error(`Failed to ${action} post!`);
        }
      );
    },
    resetPostForm() {
      this.post_id = null;
      this.title = '';
      this.content = '';
      this.selectedTags = '';
      this.url = '';
      this.image_url = '';
      this.is_private = false;
      this.postCreateError = false;
    },
    fetchTags(post) {
      if (this.$store.getters.tags.length === 0) {
        this.$store.dispatch('fetchTags');
      }
    },
    addTag(newTag) {
      const tag = {
        name: newTag,
      };
      this.$http.post('/api/tags', tag).then(
        response => {
          this.selectedTags.push(response.data);
        },
        error => {
          this.$toast.error('Failed to create tag!');
        }
      );
    },
    validateUrl(url) {
      return url.match(/^(ftp|http|https):\/\/[^ "]+$/);
    },
  },
  computed: {
    postToEdit() {
      return this.$store.getters.postToEdit;
    },
  },
  watch: {
    url: function (url) {
      if (url && !this.validateUrl(url)) {
        this.url = '';
        this.$toast.error('Invalid URL!');
      } else if (url && this.validateUrl(url)) {
        this.isBusy = true;
        this.$http.post('/api/posts/fetch-meta', { url: url }).then(
          response => {
            let meta = response.data;
            this.title = meta.ogTitle;
            this.content = meta.ogDescription;
            this.isBusy = false;
            this.image_url = meta.ogImage;
          },
          error => {
            this.isBusy = false;
            this.$toast.error('Failed to fetch meta data!');
          }
        );
      }
    },
    postToEdit: function (post) {
      if (post) {
        this.editMode = true;
        this.post_id = post.id;
        this.title = post.title;
        this.content = post.content;
        this.selectedTags = post.tags;
        this.url = post.url;
        this.image_url = post.image_url;
        this.is_private = post.is_private;
      } else {
        this.editMode = false;
        this.resetPostForm();
      }
    },
  },
};
</script>
