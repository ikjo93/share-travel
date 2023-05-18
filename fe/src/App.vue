<template>
  <div class="app">
    <AppHeader></AppHeader>
    <div class="app-contents">
      <router-view></router-view>
    </div>
    <AppFooter></AppFooter>
  </div>
</template>

<script>
import AppHeader from './components/common/AppHeader.vue';
import AppFooter from './components/common/AppFooter.vue';
import { reissueAccessToken } from '@/api/index';
import { getUserInfo } from '@/api/user';
import { getAccessTokenFromCookie } from '@/utils/cookies';

export default {
  components: {
    AppHeader,
    AppFooter,
  },
  methods: {
    async login() {
      try {
        // OAuth2 로그인 완료 이후 Access Token 추출
        const token = getAccessTokenFromCookie();
        // 액세스 토큰이 존재하면
        if (token) {
          this.processLogin(token);
          // 액세스 토큰이 존재하지 않으면
        } else {
          // 서버에 리프레쉬 토큰 전송
          const response = await reissueAccessToken.post();

          // 리프레쉬 토큰을 통해 성공적으로 액세스 토큰을 재발급 받은 경우
          const data = response.data;
          if (data.code === 'A05') {
            try {
              this.processLogin(data.accessToken);
            } catch (error) {
              this.processLogout();
            }
          } else {
            alert(data.message);
          }
        }
      } catch (error) {
        this.processLogout();
      }
    },
    async processLogin(token) {
      // 액세스 토큰을 스토어에 저장하고 사용자 정보를 가져옴
      this.$store.commit('LOGIN', token);
      const user = await getUserInfo();
      this.$store.commit('SET_USER', user.data);
    },
    processLogout() {
      this.$store.commit('LOGOUT');
      if (this.$route.path !== '/') {
        this.$router.push('/');
      }
    },
  },
  created() {
    // 새로고침 시에도 리프레쉬 토큰이 존재하는 경우 액세스 토큰을 재발급 받음
    this.login();
  },
};
</script>

<style>
@font-face {
  font-family: 'hanna-pro';
  src: url('../public/fonts/ttf/BMHANNAPro.ttf');
}
/* 배민 글씨체 적용 */
.app {
  font-family: 'hanna-pro';
}
</style>
