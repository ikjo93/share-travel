<template>
  <div></div>
</template>

<script>
import { oauth2Login } from '@/api/auth';

export default {
  methods: {
    async login() {
      try {
        const provider = this.$route.params.provider;
        const code = this.$route.query.code;
        const response = await oauth2Login(provider, code);

        console.log(response.data);

        if (response.data.nickname) {
          const user = {
            id: response.data.id,
            name: response.data.name,
            nickname: response.data.nickname,
            email: response.data.email,
            picture: response.data.picture,
          };
          this.$store.commit('SET_USER', user);
          this.$store.commit('SET_TOKEN', response.data.token);
        } else {
          this.$router.push('/userinput');
        }
      } catch (error) {
        alert('로그인 실패!');
      }

      this.$router.push('/');
    },
  },
  created() {
    this.login();
  },
};
</script>

<style></style>
