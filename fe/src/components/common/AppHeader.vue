<template>
  <div>
    <b-navbar toggleable="lg" type="light" variant="light">
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
          <b-nav-item to="/board">커뮤니티</b-nav-item>
          <template v-if="isLoggedIn">
            <UserInputModal v-if="!hasNickName"></UserInputModal>
            <b-nav-item to="/user">마이페이지</b-nav-item>
            <b-nav-item href="javascript:;" @click="logout"
              >로그아웃</b-nav-item
            >
          </template>
          <template v-else>
            <LoginModal></LoginModal>
          </template>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { deleteCookie } from '@/utils/cookies';
import LoginModal from '@/components/user/LoginModal.vue';
import UserInputModal from '@/components/user/UserInputModal.vue';

export default {
  components: {
    LoginModal,
    UserInputModal,
  },
  computed: {
    ...mapGetters(['isLoggedIn']),
    ...mapGetters(['hasNickName']),
  },
  data() {
    return {
      navHeight: 0,
      isFixed: false,
    };
  },
  methods: {
    logout() {
      this.$store.commit('LOGOUT');
      if (this.$route.path !== '/') {
        this.$router.push('/');
      }
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
body {
  padding-top: 0px;
}
</style>
