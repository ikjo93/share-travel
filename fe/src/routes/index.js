import Vue from 'vue';
import Router from 'vue-router';
import MainPage from '../views/MainPage';
import UserPage from '../views/UserPage';
import OAuthLoginPage from '../views/OAuthLoginPage';
import UserInputPage from '../views/UserInputPage';
import ShareTravelPage from '../views/ShareTravelPage';
import TravelRecommendPage from '../views/TravelRecommendPage';
import BoardPage from '../views/BoardPage';

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
      path: '/user',
      name: 'user',
      component: UserPage,
    },
    {
      path: '/login',
      name: 'login',
      component: OAuthLoginPage,
    },
    {
      path: '/userinput',
      name: 'userInput',
      component: UserInputPage,
    },
    {
      path: '/info',
      name: 'info',
      redirect: '/main',
    },
    {
      path: '/event',
      name: 'event',
      redirect: '/main',
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
    {
      path: '/board',
      name: 'board',
      component: BoardPage,
    },
  ],
});
