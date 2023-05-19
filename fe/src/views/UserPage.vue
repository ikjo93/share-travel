<template>
  <div>
    <div class="vertical-container">
      <div class="navigation">
        <ul>
          <li>
            <a href="#" class="nav-link">
              <div class="nav-item">
                <img src="/mypage/user.png" alt="Icon" class="nav-icon" />
                <span>내 정보</span>
              </div>
            </a>
          </li>
          <li>
            <a href="#" class="nav-link">
              <div class="nav-item">
                <img src="/mypage/board.png" alt="Icon" class="nav-icon" />
                <span>내 게시글 목록</span>
              </div>
            </a>
          </li>
          <li>
            <a href="#" class="nav-link">
              <div class="nav-item">
                <img src="/mypage/comment.png" alt="Icon" class="nav-icon" />
                <span>내가 등록한 댓글 목록</span>
              </div>
            </a>
          </li>
          <li>
            <a href="#" class="nav-link">
              <div class="nav-item">
                <img src="/mypage/travel.png" alt="Icon" class="nav-icon" />
                <span>나의 여행지 목록</span>
              </div>
            </a>
          </li>
          <li>
            <a href="#" class="nav-link">
              <div class="nav-item">
                <img src="/mypage/review.png" alt="Icon" class="nav-icon" />
                <span>내가 등록한 리뷰 목록</span>
              </div>
            </a>
          </li>
          <li class="nav-bottom">
            <a href="#" class="nav-link out">
              <div class="nav-item">
                <img src="/out-icon.png" alt="Icon" class="nav-icon" />
                <span>회원 탈퇴</span>
              </div>
            </a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.vertical-container {
  display: flex;
  padding-left: 10px;
  padding-top: 10px;
}

.navigation {
  flex: 0 0 300px;
  /* Adjust the width and height as needed */
  background: #f0f0f0;
  padding: 20px;
  height: 700px;
  width: auto;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.navigation ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.navigation ul li {
  margin-bottom: 20px;
  padding: 10px;
  width: 275px;
}

.nav-bottom {
  margin-top: 70%;
}

.nav-link {
  display: block;
  padding: 10px;
  background-color: #eaeaea;
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
