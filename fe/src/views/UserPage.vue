<template>
  <div>
    <b-container class="bv-example-row">
      <b-row>
        <b-col cols="4">
          <div class="vertical-container">
            <div class="navigation">
              <ul>
                <li>
                  <router-link to="info" class="nav-link">
                    <div class="nav-item">
                      <img src="/mypage/user.png" alt="Icon" class="nav-icon" />
                      <span>내 정보</span>
                    </div>
                  </router-link>
                </li>
                <li>
                  <router-link to="board" class="nav-link">
                    <div class="nav-item">
                      <img
                        src="/mypage/board.png"
                        alt="Icon"
                        class="nav-icon"
                      />
                      <span>내 게시글 목록</span>
                    </div>
                  </router-link>
                </li>
                <li>
                  <router-link to="comment" class="nav-link">
                    <div class="nav-item">
                      <img
                        src="/mypage/comment.png"
                        alt="Icon"
                        class="nav-icon"
                      />
                      <span>내가 등록한 댓글 목록</span>
                    </div>
                  </router-link>
                </li>
                <li>
                  <router-link to="travel" class="nav-link">
                    <div class="nav-item">
                      <img
                        src="/mypage/travel.png"
                        alt="Icon"
                        class="nav-icon"
                      />
                      <span>나의 여행지 목록</span>
                    </div>
                  </router-link>
                </li>
                <li>
                  <router-link to="review" class="nav-link">
                    <div class="nav-item">
                      <img
                        src="/mypage/review.png"
                        alt="Icon"
                        class="nav-icon"
                      />
                      <span>내가 등록한 리뷰 목록</span>
                    </div>
                  </router-link>
                </li>
                <li @click="dropUser">
                  <a href="javascript:;" class="nav-link">
                    <div class="nav-item">
                      <img src="/out-icon.png" alt="Icon" class="nav-icon" />
                      <span>회원 탈퇴</span>
                    </div>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </b-col>
        <b-col cols="8">
          <div class="content-container">
            <router-view></router-view>
          </div>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  methods: {
    dropUser() {
      console.log('todo');
    },
  },
};
</script>

<style scoped>
.vertical-container {
  padding-left: 10px;
  padding-top: 15px;
  padding-bottom: 15px;
}

.navigation {
  /* Adjust the width and height as needed */
  background: #f9f9f9;
  padding: 20px;
  height: 800px;
  margin: auto;
}

.navigation ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.navigation ul li {
  margin-bottom: 20px;
  padding: 10px;
  width: 300px;
}

.nav-link {
  display: block;
  padding: 10px;
  background-color: #f2f2f2;
  border-radius: 5px;
  color: #333;
  text-decoration: none;
  transition: background-color 0.3s ease;
}

.nav-link:hover {
  background-color: #ddd;
}

.nav-item {
  display: flex;
  align-items: center;
}

.nav-icon {
  margin-right: 10px;
  width: 20px;
  height: 20px;
}

.nav-item span {
  font-weight: bold;
}
</style>
