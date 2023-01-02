<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-6">
        <form @submit.prevent="register">
          <h1>Register</h1>
          <div v-if="registerSuccess">
            <div class="alert alert-success">Registration success!</div>
          </div>
          <div v-if="error">
            <div class="alert alert-danger">Registration failed!</div>
          </div>
          <div v-if="errorEmailExists">
            <div class="alert alert-danger">Email is already in use!</div>
          </div>
          <div class="form-group">
            <label for="login">Login</label>
            <input type="text" class="form-control" id="login" v-model="login" required />
          </div>
          <!-- First Name -->
          <div class="form-group">
            <label for="firstName">First Name</label>
            <input type="text" class="form-control" id="firstName" v-model="firstName" required />
          </div>
          <!-- Last Name -->
          <div class="form-group">
            <label for="lastName">Last Name</label>
            <input type="text" class="form-control" id="lastName" v-model="lastName" required />
          </div>
          <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" v-model="email" required />
          </div>
          <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" v-model="password" required />
          </div>
          <div class="form-group">
            <label for="confirmPassword">Confirm password</label>
            <input type="password" class="form-control" id="confirmPassword" v-model="confirmPassword" required />
          </div>
          <div class="form-group mt-2">
            <button class="btn btn-primary" type="submit">Register</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'Register',
  data() {
    return {
      login: '',
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      confirmPassword: '',
      doNotMatch: null,
      error: null,
      errorEmailExists: null,
      registerSuccess: null,
      myModal: null,
    };
  },
  methods: {
    register() {
      if (this.password !== this.confirmPassword) {
        this.doNotMatch = 'ERROR';
      } else {
        this.doNotMatch = null;
        this.error = null;
        this.errorEmailExists = null;
        this.registerSuccess = null;
        let data = {
          login: this.login,
          firstName: this.firstName,
          lastName: this.lastName,
          email: this.email,
          password: this.password,
        };
        this.$store.dispatch('register', data).then(() => {
          this.$toast.success('Registration success!');
          this.$router.push('/');
          this.registerSuccess = 'OK';
        });
      }
    },
  },
};
</script>
