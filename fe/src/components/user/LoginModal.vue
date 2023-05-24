<template>
  <div>
    <b-nav-item id="show-btn" @click="showLoginModal"
      ><span>로그인</span></b-nav-item
    >
    <b-modal ref="my-modal" hide-footer title="로그인">
      <div class="d-block text-center" style="font-family: 'hanna-pro';">
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
      <b-button class="mt-3" variant="outline-success" block @click="naverLogin"
        ><img src="/naver_logo.png" class="login-image" />
        <span class="login-container">네이버 로그인</span></b-button
      >
      <b-button class="mt-3" variant="outline-warning" block @click="kakaoLogin"
        ><img src="/kakao_logo.png" class="login-image" />
        <span class="login-container">카카오 로그인</span></b-button
      >
      <b-button
        class="mt-3"
        variant="outline-danger"
        block
        @click="hideLoginModal"
        style="font-family: 'hanna-pro';"
        >닫기</b-button
      >
    </b-modal>
  </div>
</template>

<script>
export default {
  methods: {
    showLoginModal() {
      this.$refs['my-modal'].show();
    },
    hideLoginModal() {
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
  },
};
</script>

<style scoped>
.login-image {
  width: 15px;
  height: 15px;
}

.login-container {
  margin-left: 10px;
  font-family: 'hanna-pro';
}
</style>
