/* Vuex initialization */
import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios';

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    userIdentity: null,
    authenticated: false,
    posts: [],
    tags: [],
    currentPage: 0,
    isBusy: false,
  },
  mutations: {
    authenticate(state) {
      state.logon = true;
      state.authenticated = true;
      state.logon = true;
    },
    authenticated(state, identity) {
      state.userIdentity = identity;
      state.authenticated = true;
      state.logon = true;
    },
    logout(state) {
      state.userIdentity = null;
      state.authenticated = false;
      state.logon = false;
      localStorage.removeItem('jwt-token');
    },
    fetchPosts(state, posts) {
      state.posts.push(...posts);
    },
    fetchTags(state, tags) {
      state.tags = tags;
    },
    incrementPageNumber(state) {
      state.currentPage++;
    },
    setPageNumber(state, page) {
      state.currentPage = page;
    },
    isBusy(state, busy) {
      state.busy = busy;
    },
  },
  actions: {
    authenticate(context, data) {
      return new Promise((resolve, reject) => {
        axios
          .post('api/authenticate', data)
          .then(result => {
            const bearerToken = result.headers.authorization;
            if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
              const jwt = bearerToken.slice(7, bearerToken.length);
              localStorage.setItem('jwt-token', jwt);
              console.log('authorization token set');
              context.commit('authenticate');
            }
            this.authenticationError = false;

            context
              .dispatch('retrieveAccount')
              .then(() => {
                resolve();
              })
              .catch(error => {
                reject(error);
              });
          })
          .catch(error => {
            this.authenticationError = true;
            reject(error);
          });
      });
    },
    authenticated(context, identity) {
      context.commit('authenticated', identity);
    },
    logout(context) {
      context.commit('logout');
    },
    retrieveAccount(context) {
      axios
        .get('api/account', {
          headers: {
            Authorization: 'Bearer ' + localStorage.getItem('jwt-token') || null,
          },
        })
        .then(result => {
          const account = result.data;
          if (account) {
            context.commit('authenticated', account);
          } else {
            context.commit('logout');
          }
        })
        .catch(() => {
          context.commit('logout');
        });
    },
    register(context, data) {
      return new Promise((resolve, reject) => {
        axios
          .post('api/register', data)
          .then(result => {
            context.commit('authenticate');
            resolve(result);
          })
          .catch(error => {
            reject(error);
          });
      });
    },
    fetchPosts(context) {
      if (context.state.currentPage !== context.state.currentPage + 1) {
        context.commit('isBusy', true);
        return new Promise((resolve, reject) => {
          axios
            .get('api/posts', {
              params: {
                page: context.state.currentPage,
              },
              headers: {
                Authorization: 'Bearer ' + localStorage.getItem('jwt-token') || null,
              },
            })
            .then(result => {
              context.commit('fetchPosts', result.data);
              context.commit('incrementPageNumber');
              context.commit('isBusy', false);
              console.log('posts fetched');
              resolve(result);
            })
            .catch(error => {
              context.commit('logout');
              reject(error);
            });
        });
      }
    },
    fetchTags(context) {
      return new Promise((resolve, reject) => {
        axios
          .get('api/tags', {
            headers: {
              Authorization: 'Bearer ' + localStorage.getItem('jwt-token') || null,
            },
          })
          .then(result => {
            context.commit('fetchTags', result.data);
            console.log('tags fetched');
            resolve(result);
          })
          .catch(error => {
            context.commit('logout');
            reject(error);
          });
      });
    },
  },
  getters: {
    authenticated: state => {
      return state.authenticated;
    },
    userIdentity: state => {
      return state.userIdentity;
    },
    logon: state => {
      return state.logon;
    },
    posts: state => {
      return state.posts;
    },
    tags: state => {
      return state.tags;
    },
    isBusy: state => {
      return state.isBusy;
    },
  },
});

export default store;
