<template>
  <div></div>
</template>

<script>
import { getUserInfo } from '@/api/user';

export default {
  methods: {
    async login() {
      try {
        this.$store.commit('LOGIN');
        const response = await getUserInfo();
        this.$store.commit('SET_USER', response.data);

        if (this.$store.getters.hasNickName) {
          this.$router.push('/');
        } else {
          this.$router.push('/userinput');
        }
      } catch (error) {
        alert('로그인 실패!');
        this.$router.push('/');
      }
    },
  },
  created() {
    this.login();
  },
};
</script>

<style></style>
