<template>
  <div>
    <h1>Activation</h1>
    <p v-if="key && !errorMessage">Activating key: {{ key }}</p>
    <div v-if="key && isBusy && !errorMessage" class="spinner-border" role="status">
      <span class="visually-hidden">Loading...</span>
    </div>
    <!-- LOADING -->
    <p v-else-if="!key">Activation key is missing</p>
    <p v-if="errorMessage" class="alert alert-danger">{{ errorMessage }}</p>
  </div>
</template>
<script>
export default {
  name: 'Activate',
  data() {
    return {
      key: null,
      isBusy: true,
      message: 'Your account is being activated. Please wait...',
      errorMessage: null,
    };
  },
  mounted() {
    this.key = this.$route.query.key;
    this.activate();
  },
  methods: {
    activate() {
      this.$http
        .get(`/api/activate?key=${this.key}`)
        .then(() => {
          this.isBusy = false;
          this.message = 'Your account has been activated. You can surf and enjoy.';
        })
        .catch(error => {
          this.isBusy = false;
          this.errorMessage = error.response.data.detail;
          console.log(error.response.data);
        });
    },
  },
};
</script>
