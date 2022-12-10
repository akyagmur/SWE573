/* Vuex initialization */
import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'


Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        userIdentity: null,
        authenticated: false,
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
        }
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
                            console.log('authorization token set')
                            context.commit('authenticate');
                        }
                        this.authenticationError = false;

                        context.dispatch('retrieveAccount')
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
            /* axios
                .post('api/authenticate', data)
                .then(result => {
                    const bearerToken = result.headers.authorization;
                    if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
                        const jwt = bearerToken.slice(7, bearerToken.length);
                        localStorage.setItem('jwt-token', jwt);
                        console.log('authorization token set')
                        context.commit('authenticate');
                    }
                    this.authenticationError = false;

                    context.dispatch('retrieveAccount');
                })
                .catch(() => {
                    this.authenticationError = true;
                }); */
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
                        'Authorization': 'Bearer ' + localStorage.getItem('jwt-token') || null
                    }
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
        }
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
    }
});

export default store;
