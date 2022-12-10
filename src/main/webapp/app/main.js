// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { onRequestSuccess, setupAxiosInterceptors } from '@/shared/config/axios-interceptor';
/* import axios */
import axios from 'axios';
import App from './app.vue';
import Vue2Filters from 'vue2-filters';
import router from './router';
import * as config from './shared/config/config';
import InfiniteLoading from 'vue-infinite-loading';

import '../content/scss/global.scss';
import '../content/scss/vendor.scss';
/* tslint:disable */
import * as bootstrap from 'bootstrap'

// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

/* tslint:enable */
Vue.config.productionTip = false;
config.initVueApp(Vue);
config.initFortAwesome(Vue);
Vue.use(Vue2Filters);
Vue.prototype.$bootstrap = bootstrap;
Vue.prototype.$http = axios;
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('infinite-loading', InfiniteLoading);
import store from './store/store';

router.beforeEach(async (to, from, next) => {
  if (!to.matched.length) {
    next('/not-found');
  } else if (to.meta) {
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

setupAxiosInterceptors(
  error => {
    const url = error.response?.config?.url;
    const status = error.status || error.response.status;
    if (status === 401) {
      // Store logged out state.
      store.commit('logout');
    }
    console.log('Unauthorized!');
    return Promise.reject(error);
  },
  error => {
    console.log('Server error!');
    return Promise.reject(error);
  }
);
