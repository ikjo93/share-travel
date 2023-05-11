import Vue from 'vue';
import Router from 'vue-router';
import MainPage from '../views/MainPage';
import LoginPage from '../views/LoginPage';
import UserPage from '../views/UserPage';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/main',
    },
    {
      path: '/main',
      name: 'main',
      component: MainPage,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginPage,
    },
    {
      path: '/user',
      name: 'user',
      component: UserPage,
    },
  ],
});
