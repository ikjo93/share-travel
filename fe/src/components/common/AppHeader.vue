<template>
  <header :class="{ fixed: isFixed }" ref="appHeader">
    <div>
      <img class="logo-image" src="/logo.png" />
      <router-link to="/" class="logo">
        Share-Travel
      </router-link>
    </div>
    <div class="navigations">
      <template v-if="isLoggedIn">
        <a href="javascript:;" @click="logout" class="logout-button"
          >로그아웃</a
        >
      </template>
      <template v-else>
        <router-link to="/login">로그인</router-link>
        <router-link to="/user">마이페이지</router-link>
      </template>
    </div>
  </header>
</template>

<script>
export default {
  data() {
    return {
      navHeight: 0,
      isFixed: false,
    };
  },
};
</script>

<style scoped>
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #927dfc;
  z-index: 2;
  box-shadow: 0px 3px 10px rgba(0, 0, 0, 0.05);
}

a {
  color: #dedede;
  font-size: 18px;
}

.logo-image {
  width: 30px;
  height: 30px;
  vertical-align: -5px;
}

a.logo {
  font-size: 30px;
  font-weight: 900;
  color: white;
}

.logo > span {
  font-size: 14px;
  font-weight: normal;
}

.navigations a {
  margin-left: 10px;
}

.fixed {
  position: fixed;
  top: 0;
  width: 100%;
}

.add-button {
  background: #3ca776;
  color: white;
  padding: 4px 23px;
  border-radius: 4px;
}

.logout-button {
  font-size: 14px;
}

a.router-link-exact-active {
  color: white;
  font-weight: bold;
}
</style>
