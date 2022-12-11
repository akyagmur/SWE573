// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
/* import axios */
import axios from 'axios';
import App from './app.vue';
import Vue2Filters from 'vue2-filters';
import router from './router';
import InfiniteLoading from 'vue-infinite-loading';

import '../content/scss/global.scss';
import '../content/scss/vendor.scss';
/* tslint:disable */
import * as bootstrap from 'bootstrap'

// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

/* tslint:enable */
Vue.config.productionTip = false;
Vue.use(Vue2Filters);
Vue.prototype.$bootstrap = bootstrap;
Vue.prototype.$http = axios;
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('infinite-loading', InfiniteLoading);
import store from './store/store';

router.beforeEach(async (to, from, next) => {
  if (!to.matched.length) {
    next('/not-found');
  } else if (to.meta && localStorage.getItem('jwt-token') && localStorage.getItem('jwt-token') !== 'undefined') {
    store.dispatch('retrieveAccount').then(() => {
      if (to.meta.authorities && to.meta.authorities.length > 0 && !store.getters.hasAnyAuthority(to.meta.authorities)) {
        next('/accessdenied');
      } else {
        next();
      }
    });
    next();
  }
  next();
});

/* tslint:disable */
const vue = new Vue({
  el: '#app',
  components: { App },
  template: '<App/>',
  router,
  store
});
