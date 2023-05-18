import Vue from 'vue';
import Vuex from 'vuex';

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
    LOGIN(state, token) {
      state.token = token;
    },
    LOGOUT(state) {
      state.user = null;
      state.token = null;
    },
  },
});
