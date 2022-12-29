<template>
  <div class="modal" id="loginModal" tabindex="-1" aria-labelledby="loginModal" aria-hidden="true" ref="modal">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="loginModal">Login</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <!-- login form -->
          <!-- show error -->
          <div class="row justify-content-center">
            <div class="col-md-12">
              <div class="alert alert-danger" role="alert" v-if="authenticationError">
                <strong>Failed to sign in!</strong> Please check your credentials and try again.
              </div>
            </div>
          </div>
          <form>
            <div class="mb-3 row">
              <label for="username" class="col-sm-2 col-form-label">Username</label>
              <div class="col-sm-10">
                <input v-model="login" type="text" class="form-control" id="username" />
              </div>
            </div>
            <div class="mb-3 row">
              <label for="password" class="col-sm-2 col-form-label">Password</label>
              <div class="col-sm-10">
                <input v-model="password" type="password" class="form-control" id="password" />
              </div>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" v-on:click="doLogin">Login</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'Login',
  data() {
    return {
      login: '',
      password: '',
      authenticationError: false,
      myModal: null,
    };
  },
  methods: {
    doLogin() {
      let data = {
        username: this.login,
        password: this.password,
      };

      this.$store
        .dispatch('authenticate', data)
        .then(() => {
          this.authenticationError = false;
          this.$bootstrap.Modal.getInstance(this.$refs.modal).hide();
          this.$store.dispatch('fetchLikedPostsOfUser');
          this.$store.dispatch('fetchBookmarksOfUser');
        })
        .catch(() => {
          this.authenticationError = true;
        });
    },
  },
};
</script>
