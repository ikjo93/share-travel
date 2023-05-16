import Vue from 'vue';
import Vuex from 'vuex';

import { deleteCookie } from '@/utils/cookies.js';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: {},
    token: '',
  },
  getters: {
    isLoggedIn(state) {
      return !!state.token;
    },
    userToken(state) {
      return state.token;
    },
  },
  mutations: {
    SET_USER(state, user) {
      state.user = user;
    },
    SET_TOKEN(state, token) {
      state.token = token;
    },
    LOGOUT(state) {
      state.user = null;
      state.token = null;
      deleteCookie('renew');
    },
  },
});
