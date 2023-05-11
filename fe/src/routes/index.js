import Vue from 'vue';
import Router from 'vue-router';
import MainPage from '../views/MainPage';
import LoginPage from '../views/LoginPage';
import UserPage from '../views/UserPage';
import ShareTravelPage from '../views/ShareTravelPage';
import TravelRecommendPage from '../views/TravelRecommendPage';

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
    {
      path: '/share',
      name: 'share',
      component: ShareTravelPage,
    },
    {
      path: '/recommend',
      name: 'recommend',
      component: TravelRecommendPage,
    },
  ],
});
