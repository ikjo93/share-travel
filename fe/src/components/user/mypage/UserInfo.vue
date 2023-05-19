<template>
  <div class="profile-container">
    <div class="profile-header">
      <img
        class="profile-picture"
        :src="this.$store.state.user.picture"
        alt="Profile Picture"
      />
    </div>
    <div class="profile-info">
      <h2>{{ this.$store.state.user.nickName }}</h2>
      <p>{{ this.$store.state.user.email }}</p>
    </div>
    <div class="profile-modify-button">
      <button @click="openProfileModal">수정</button>
    </div>
    <div class="keyword-container">
      <h3>나의 여행지 키워드</h3>
      <ul>
        <li
          v-for="keyword in this.$store.state.user.keywords"
          :key="keyword.id"
        >
          {{ keyword.name }}
        </li>
      </ul>
    </div>
    <div class="keyword-modify-button">
      <button @click="openKeywordModal">수정</button>
    </div>
    <div class="server-info">
      <p>Logged in with: {{ this.$store.state.user.oauthProvider }}</p>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    openProfileModal() {
      // Code to open the profile modification modal
    },
    openKeywordModal() {
      // Code to open the keyword modification modal
    },
  },
};
</script>

<style>
.profile-container {
  text-align: center;
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
  margin-top: 20px;
}

.profile-modify-button {
  margin-top: 10px;
}

.keyword-container {
  margin-top: 30px;
}

.keyword-container li {
  list-style: none;
}

.keyword-modify-button {
  margin-top: 10px;
}

.server-info {
  margin-top: 30px;
}
</style>
