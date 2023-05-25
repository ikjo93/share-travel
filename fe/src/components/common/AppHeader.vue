<template>
  <div>
    <b-navbar toggleable="lg" type="light" variant="light">
      <b-navbar-brand to="/">
        <img src="/logo.png" class="d-inline-block align-top" alt="logo" />
        Share-Travel
      </b-navbar-brand>

      <b-navbar-nav>
        <b-nav-item @click="moveCategory(2)">공지사항</b-nav-item>
        <b-nav-item @click="moveCategory(3)">이벤트</b-nav-item>
      </b-navbar-nav>

      <b-collapse id="nav-collapse" is-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-item to="/share">여행지 찾기</b-nav-item>
          <b-nav-item to="/recommend">여행지 추천</b-nav-item>
          <b-nav-item @click="moveCategory(0)">커뮤니티</b-nav-item>
          <template v-if="isLoggedIn">
            <b-nav-item to="/user">마이페이지</b-nav-item>
            <b-nav-item href="javascript:;" @click="userLogout"
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
import { logout } from '@/api/auth';
import LoginModal from '@/components/user/LoginModal.vue';

export default {
  components: {
    LoginModal,
  },
  computed: {
    ...mapGetters(['isLoggedIn']),
  },
  methods: {
    moveCategory(idx) {
      if (idx == 0) {
        this.$store.commit('SET_CATEGORY_ID', 1);
        this.$router.push({ name: 'boardgeneral' });
      } else if (idx == 1) {
        this.$store.commit('SET_CATEGORY_ID', 2);
        this.$router.push({ name: 'boardtip' });
      } else if (idx == 2) {
        this.$store.commit('SET_CATEGORY_ID', 3);
        this.$router.push({ name: 'boardnotice' });
      } else if (idx == 3) {
        this.$store.commit('SET_CATEGORY_ID', 4);
        this.$router.push({ name: 'boardevent' });
      }
    },
    async userLogout() {
      try {
        await logout();
        this.$store.commit('LOGOUT');
        alert('로그아웃이 처리되었습니다!');
      } catch (error) {
        this.$store.commit('LOGOUT');
        alert('로그아웃을 처리하는 과정에서 에러가 발생했습니다.');
      }

      if (this.$route.path !== '/') {
        this.$router.push('/');
      }
    },
  },
};
</script>

<style>
.navbar-brand img {
  height: 30px;
  width: 40px;
}
</style>
