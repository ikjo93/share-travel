import Vue from 'vue';
import Router from 'vue-router';
import store from '@/store/index';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: () => import('@/views/MainPage.vue'),
    },
    {
      path: '/info',
      name: 'info',
      redirect: '/',
    },
    {
      path: '/event',
      name: 'event',
      redirect: '/',
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
          component: () => import('@/components/user/mypage/UserInfo.vue'),
          beforeEnter,
        },
        {
          path: 'board',
          name: 'userBoard',
          component: () => import('@/components/user/mypage/UserBoard.vue'),
          beforeEnter,
        },
        {
          path: 'comment',
          name: 'userBoardComment',
          component: () =>
            import('@/components/user/mypage/UserBoardComment.vue'),
          beforeEnter,
        },
        {
          path: 'travel',
          name: 'userTravel',
          component: () => import('@/components/user/mypage/UserTravel.vue'),
          beforeEnter,
        },
        {
          path: 'review',
          name: 'userTravelReview',
          component: () =>
            import('@/components/user/mypage/UserTravelReview.vue'),
          beforeEnter,
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
          beforeEnter,
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

function beforeEnter(to, from, next) {
  if (store.getters['isLoggedIn']) {
    next();
  } else {
    alert('로그인이 필요한 요청입니다.');
    next('/');
  }
}
