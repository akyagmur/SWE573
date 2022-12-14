// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { far } from '@fortawesome/free-regular-svg-icons';

library.add(fas);
library.add(far);

/* import axios */
import axios from 'axios';
import App from './app.vue';
import Vue2Filters from 'vue2-filters';
import router from './router';
import InfiniteLoading from 'vue-infinite-loading';

import '../content/scss/global.scss';
import '../content/scss/vendor.scss';
/* tslint:disable */
import * as bootstrap from 'bootstrap';
import VueToast from 'vue-toast-notification';
// Import one of the available themes
//import 'vue-toast-notification/dist/theme-default.css';
import 'vue-toast-notification/dist/theme-sugar.css';
// vue-multiselect/dist/vue-multiselect.min.css
import 'vue-multiselect/dist/vue-multiselect.min.css';
// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here
import infiniteScroll from 'vue-infinite-scroll';
Vue.use(infiniteScroll);
Vue.config.productionTip = false;
Vue.use(Vue2Filters);
Vue.use(VueToast);
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
  store,
});

Vue.mixin({
  methods: {
    convertDateToHumanReadable: function (date) {
      return new Date(date).toLocaleDateString('en-gb', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        timeZone: 'utc',
      });
    },
    truncateString: function (str, length) {
      if (str.length > length) {
        return str.substring(0, length) + '...';
      }
      return str;
    },
    isUsersPost(userId) {
      return this.$store.getters.userIdentity?.id === userId;
    },
  },
});

/* add authorization header to axios */
axios.interceptors.request.use(
  config => {
    if (localStorage.getItem('jwt-token') && localStorage.getItem('jwt-token') !== 'undefined') {
      config.headers = {
        Authorization: 'Bearer ' + localStorage.getItem('jwt-token'),
      };
    }
    return config;
  },
  error => {
    Promise.reject(error);
  }
);
