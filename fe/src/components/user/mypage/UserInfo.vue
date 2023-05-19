<template>
  <div class="profile-container">
    <div class="profile-header">
      <img class="profile-picture" :src="user.picture" alt="Profile Picture" />
    </div>
    <div class="profile-info nickname">
      <p>닉네임 : {{ user.nickName }}</p>
    </div>
    <div class="profile-info email">
      <p>이메일 : {{ user.email }}</p>
    </div>
    <div class="keyword-container">
      <h2>나의 여행지 키워드</h2>
      <div class="grid-container">
        <button
          class="radious grid-item"
          v-for="keyword in user.keywords"
          :key="keyword.id"
        >
          {{ keyword.name }}
        </button>
      </div>
    </div>
    <UserUpdateModal></UserUpdateModal>
    <div class="login-info">
      <p>
        Logged in with: {{ user.oauthProvider }}
        <template v-if="user.oauthProvider === 'GOOGLE'"
          ><img src="/google_logo.png" class="login-image" alt="logo"
        /></template>
        <template v-else-if="user.oauthProvider === 'NAVER'"
          ><img src="/naver_logo.png" class="login-image" alt="logo"
        /></template>
        <template v-else-if="user.oauthProvider === 'KAKAO'"
          ><img src="/kakao_logo.png" class="login-image" alt="logo"
        /></template>
      </p>
    </div>
  </div>
</template>

<script>
import UserUpdateModal from '@/components/user/mypage/UserUpdateModal';

export default {
  components: {
    UserUpdateModal,
  },
  computed: {
    user() {
      return this.$store.state.user;
    },
  },
};
</script>

<style>
.login-image {
  width: 15px;
  height: 15px;
  margin-left: 5px;
}

.profile-container {
  background-color: #f9f9f9;
  text-align: center;
  height: 800px;
  margin: 15px;
  padding: 15px;
}

.profile-header {
  margin-top: 20px;
}

.profile-picture {
  width: 150px;
  height: 150px;
  border-radius: 50%;
}

.profile-info {
  margin: auto;
  background-color: #f2f2f2;
  width: 60%;
  height: auto;
  padding: 10px 10px;
  margin-top: 50px;
  font-size: 20px;
}

.profile-info p {
  margin-bottom: 5px;
}

.keyword-container {
  margin-top: 50px;
}

.keyword-container h3 {
  font-size: 24px;
  font-weight: bold;
}

.keyword-container ul {
  list-style-type: none;
  padding: 0;
  margin-top: 10px;
}

.keyword-container li {
  margin-bottom: 5px;
  background-color: #f2f2f2;
  padding: 5px;
  border-radius: 5px;
  display: inline-block;
}

.keyword-modify-button {
  margin-top: 50px;
}

.keyword-modify-button button {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 8px 16px;
  font-size: 16px;
  border-radius: 4px;
  cursor: pointer;
}

.login-info {
  margin-top: 50px;
  font-size: 14px;
}

.radious {
  cursor: default;
  border-radius: 50px !important;
  padding: 5px 12px !important;
  overflow-x: auto !important;
  margin: 5px !important;
  white-space: nowrap !important;
  border: 0px !important;
}

.grid-container {
  display: flex;
  justify-content: center; /* Align items horizontally (in this case, center) */
  align-items: center; /* Align items vertically (in this case, center) */
  flex-wrap: wrap;
}
</style>
