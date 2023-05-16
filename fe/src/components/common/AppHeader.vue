<template>
  <div>
    <b-navbar fixed="top" toggleable="lg" type="light" variant="light">
      <b-navbar-brand to="/">
        <img src="/logo.png" class="d-inline-block align-top" alt="logo" />
        Share-Travel
      </b-navbar-brand>

      <b-navbar-nav>
        <b-nav-item to="/info">공지사항</b-nav-item>
        <b-nav-item to="/event">이벤트</b-nav-item>
      </b-navbar-nav>

      <b-collapse id="nav-collapse" is-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-item to="/share">여행지 찾기</b-nav-item>
          <b-nav-item to="/recommend">여행지 추천</b-nav-item>
          <b-nav-item to="/board">게시판</b-nav-item>
          <template v-if="isLoggedIn">
            <b-nav-item to="/user">마이페이지</b-nav-item>
            <b-nav-item href="javascript:;" @click="logout"
              >로그아웃</b-nav-item
            >
          </template>
          <template v-else>
            <b-nav-item id="show-btn" @click="showModal">로그인</b-nav-item>
            <b-modal ref="my-modal" hide-footer title="로그인">
              <div class="d-block text-center">
                <h3>Welcome to the Share Travel!!!</h3>
                <img src="/logo.png" width="150px" alt="logo" />
              </div>
              <b-button
                class="mt-3"
                variant="outline-primary"
                block
                @click="googleLogin"
                ><img src="/google_logo.png" class="login-image" />
                <span class="login-container">구글 로그인</span></b-button
              >
              <b-button
                class="mt-3"
                variant="outline-success"
                block
                @click="naverLogin"
                ><img src="/naver_logo.png" class="login-image" />
                <span class="login-container">네이버 로그인</span></b-button
              >
              <b-button
                class="mt-3"
                variant="outline-warning"
                block
                @click="kakaoLogin"
                ><img src="/kakao_logo.png" class="login-image" />
                <span class="login-container">카카오 로그인</span></b-button
              >
              <b-button
                class="mt-3"
                variant="outline-danger"
                block
                @click="hideModal"
                >닫기</b-button
              >
            </b-modal>
          </template>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { deleteCookie } from '@/utils/cookies';

export default {
  computed: {
    ...mapGetters(['isLoggedIn']),
  },
  data() {
    return {
      navHeight: 0,
      isFixed: false,
    };
  },
  methods: {
    showModal() {
      this.$refs['my-modal'].show();
    },
    hideModal() {
      this.$refs['my-modal'].hide();
    },
    googleLogin() {
      location.href = `${process.env.VUE_APP_API_URL}oauth2/authorization/google`;
    },
    naverLogin() {
      location.href = `${process.env.VUE_APP_API_URL}oauth2/authorization/naver`;
    },
    kakaoLogin() {
      location.href = `${process.env.VUE_APP_API_URL}oauth2/authorization/kakao`;
    },
    logout() {
      this.$store.commit('LOGOUT');
      this.$router.push('/');
      deleteCookie('renew');
    },
  },
};
</script>

<style>
.navbar-brand img {
  height: 30px;
  width: 40px;
}
.login-image {
  width: 15px;
  height: 15px;
}
.login-container {
  margin-left: 10px;
}
</style>
