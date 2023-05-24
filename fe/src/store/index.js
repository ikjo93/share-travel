import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: {},
    token: '',
    categoryId: '',
  },
  getters: {
    isLoggedIn(state) {
      return !!state.token;
    },
    userToken(state) {
      return state.token;
    },
    hasNecessaryUserInfo(state) {
      if (!state.user || !state.user.nickName || !state.user.keywords) {
        return false;
      }
      const length = state.user.keywords.length;
      return 1 <= length && length <= 3;
    },
    getCategoryId(state) {
      return state.categoryId;
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
    SET_CATEGORY_ID(state, categoryId) {
      state.categoryId = categoryId;
    },
  },
});
