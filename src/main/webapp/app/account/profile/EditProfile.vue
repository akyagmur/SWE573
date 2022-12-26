<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-6">
        <form @submit.prevent="save">
          <h1>Edit Profile</h1>

          <div class="form-group">
            <label for="login">Login</label>
            <input type="text" class="form-control" id="login" v-model="userAccount.login" required />
          </div>
          <!-- First Name -->
          <div class="form-group">
            <label for="firstName">First Name</label>
            <input type="text" class="form-control" id="firstName" v-model="userAccount.firstName" required />
          </div>
          <!-- Last Name -->
          <div class="form-group">
            <label for="lastName">Last Name</label>
            <input type="text" class="form-control" id="lastName" v-model="userAccount.lastName" required />
          </div>
          <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" v-model="userAccount.email" required />
          </div>
          <!-- Password -->
          <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" v-model="userAccount.password" />
          </div>
          <div class="form-group">
            <label for="confirmPassword">Confirm password</label>
            <input type="password" class="form-control" id="confirmPassword" v-model="userAccount.confirmPassword" />
          </div>
          <div class="form-group mt-2">
            <button class="btn btn-primary" type="submit">Save</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'EditProfile',
  data() {
    return {
      userAccount: {},
    };
  },
  mounted() {
    this.$store.dispatch('retrieveAccount').then(account => {
      this.userAccount = account;
    });
  },
  methods: {
    save() {
      this.$http.post('api/account', this.userAccount).then(() => {
        this.$store.dispatch('retrieveAccount').then(account => {
          this.userAccount = account;
          this.$toast.success('Profile saved!');
        });
      });
    },
  },
};
</script>
