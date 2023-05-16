import Vue from 'vue';
import Vuex from 'vuex';

import { getAccessTokenFromCookie, deleteCookie } from '@/utils/cookies';

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
    hasNickName(state) {
      return !!state.user.nickName;
    },
  },
  mutations: {
    SET_USER(state, user) {
      state.user = user;
    },
    LOGIN(state) {
      const token = getAccessTokenFromCookie();
      state.token = token;
      deleteCookie('auth');
    },
    LOGOUT(state) {
      state.user = null;
      state.token = null;
      deleteCookie('renew');
    },
  },
});
