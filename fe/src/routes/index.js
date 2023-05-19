import Vue from 'vue';
import Router from 'vue-router';
import MainPage from '../views/MainPage';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: MainPage,
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
      component: () => import('@/views/ShareTravelPage.vue'),
    },
    {
      path: '/recommend',
      name: 'recommend',
      component: () => import('@/views/TravelRecommendPage.vue'),
    },
    {
      path: '/user',
      name: 'user',
      component: () => import('@/views/UserPage.vue'),
      redirect: '/user/info',
      children: [
        {
          path: 'info',
          name: 'userInfo',
          component: () => import('@/components/user/UserInfo.vue'),
        },
        {
          path: 'board',
          name: 'userBoard',
          component: () => import('@/components/user/UserBoard.vue'),
        },
        {
          path: 'comment',
          name: 'userBoardComment',
          component: () => import('@/components/user/UserBoardComment.vue'),
        },
        {
          path: 'travel',
          name: 'userTravel',
          component: () => import('@/components/user/UserTravel.vue'),
        },
        {
          path: 'review',
          name: 'userTravelReview',
          component: () => import('@/components/user/UserTravelReview.vue'),
        },
      ],
    },
    {
      path: '/board',
      name: 'board',
      component: () => import('@/views/BoardPage.vue'),
      redirect: '/board/notice',
      children: [
        {
          path: 'notice',
          name: 'boardnotice',
          component: () => import('@/components/board/content/BoardNotice.vue'),
        },
        {
          path: 'event',
          name: 'boardevent',
          component: () => import('@/components/board/content/BoardEvent.vue'),
        },
        {
          path: 'general',
          name: 'boardgeneral',
          component: () =>
            import('@/components/board/content/BoardGeneralForum.vue'),
        },
        {
          path: 'tip',
          name: 'boardtip',
          component: () =>
            import('@/components/board/content/BoardTipForum.vue'),
        },
        {
          path: 'detail',
          name: 'boarddetail',
          component: () => import('@/components/board/content/BoardDetail.vue'),
        },
        {
          path: 'write',
          name: 'boardwrite',
          component: () => import('@/components/board/BoardWrite.vue'),
        },
        {
          path: 'qna',
          name: 'boardqna',
          component: () => import('@/components/board/content/BoardQna.vue'),
        },
      ],
    },
  ],
});
