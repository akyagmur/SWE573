<template>
  <div>
    <h1>Error Page!</h1>

    <div v-if="errorMessage">
      <div class="alert alert-danger">{{ errorMessage }}</div>
    </div>
    <div v-if="error403" class="alert alert-danger">You are not authorized to access this page.</div>
    <div v-if="error404" class="alert alert-warning">The page you requested does not exist.</div>
  </div>
</template>

<script lang="ts" src="./error.component.ts"></script>
