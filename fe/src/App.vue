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
import { refreshToken } from '@/api/index';
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
        const token = getAccessTokenFromCookie();
        // 액세스 토큰이 존재하면
        if (token) {
          this.$store.commit('LOGIN', token);
          const response = await getUserInfo();
          this.$store.commit('SET_USER', response.data);

          if (this.$store.getters.hasNickName) {
            this.$router.push('/');
          } else {
            this.$router.push('/userinput');
          }

          // 액세스 토큰이 존재하지 않으면
        } else {
          // 리프레쉬 토큰이 로그아웃 처리되지 않았다면
          if (localStorage.getItem('hasRefreshToken') === 'yes') {
            // 서버에 리프레쉬 토큰 전송
            const response = await refreshToken.post();
            const data = response.data;
            console.log(data);

            // 액세스 토큰을 재발급 받은 경우
            if (data.code === 'A05') {
              alert(data.accessToken);
              this.$store.commit('LOGIN', data.accessToken);
              const user = await getUserInfo();
              this.$store.commit('SET_USER', user.data);

              if (this.$store.getters.hasNickName) {
                this.$router.push('/');
              } else {
                this.$router.push('/userinput');
              }
            } else {
              this.$store.commit('LOGOUT');
              this.$router.push('/');
            }
          } else {
            this.$store.commit('LOGOUT');
          }
        }
      } catch (error) {
        alert('오류 발생!');
        this.$store.commit('LOGOUT');
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
