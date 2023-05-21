<template>
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
              <img src="/mypage/board.png" alt="Icon" class="nav-icon" />
              <span>내 게시글 목록</span>
            </div>
          </router-link>
        </li>
        <li>
          <router-link to="comment" class="nav-link">
            <div class="nav-item">
              <img src="/mypage/comment.png" alt="Icon" class="nav-icon" />
              <span>내가 등록한 댓글 목록</span>
            </div>
          </router-link>
        </li>
        <li>
          <router-link to="travel" class="nav-link">
            <div class="nav-item">
              <img src="/mypage/travel.png" alt="Icon" class="nav-icon" />
              <span>나의 여행지 목록</span>
            </div>
          </router-link>
        </li>
        <li>
          <router-link to="review" class="nav-link">
            <div class="nav-item">
              <img src="/mypage/review.png" alt="Icon" class="nav-icon" />
              <span>내가 등록한 리뷰 목록</span>
            </div>
          </router-link>
        </li>
        <li @click="dropUser">
          <a href="javascript:;" class="nav-link">
            <div class="nav-item">
              <img src="/mypage/out.png" alt="Icon" class="nav-icon" />
              <span>회원 탈퇴</span>
            </div>
          </a>
        </li>
      </ul>
    </div>
    <b-modal
      ref="my-modal"
      hide-footer
      hide-header
      no-close-on-backdrop
      no-close-on-esc
    >
      <div v-if="loading"><LoadingSpinner></LoadingSpinner>></div>
      <div v-else>
        <b-jumbotron
          lead="회원 탈퇴 안내 💁‍♂️"
          bg-variant="white"
          style="font-family: 'hanna-pro';"
        >
          <b-form-group
            id="fieldset-1"
            label="귀하의 계정으로 등록된 이메일로 인증코드가 발송되었습니다."
            label-for="input-1"
          >
            <b-form-input
              id="input-1"
              v-model="authCode"
              trim
              class="form-input"
            ></b-form-input>
            <b-button variant="outline-primary">
              <span>{{ TimerStr }}</span>
            </b-button>
          </b-form-group>
          <div class="button-container">
            <b-button
              variant="outline-success"
              :disabled="authCodeState"
              size="lg"
              @click="submit"
              >제출</b-button
            >
            <b-button
              size="lg"
              variant="outline-danger"
              @click="cancelAuth"
              style="margin-left: 5px;"
              >닫기</b-button
            >
          </div>
        </b-jumbotron>
      </div>
    </b-modal>
  </div>
</template>

<script>
import { getAuthCode, confirmAuthCode } from '@/api/user';
import LoadingSpinner from '@/components/common/LoadingSpinner';

export default {
  data() {
    return {
      Timer: null,
      TimeCounter: 180,
      TimerStr: '03:00',
      authCode: '',
      loading: false,
    };
  },
  components: {
    LoadingSpinner,
  },
  computed: {
    authCodeState() {
      return !this.authCode;
    },
  },
  methods: {
    async dropUser() {
      if (confirm('정말 탈퇴하시겠습니까? 😥')) {
        try {
          this.$refs['my-modal'].show();
          this.TimerStr = '03:00';
          this.authCode = '';
          this.loading = true;

          await getAuthCode();

          this.loading = false;
        } catch (error) {
          alert(
            '메일 인증 코드 발송에 실패했습니다. 로그인 여부를 확인해주세요.',
          );
          this.$refs['my-modal'].hide();
          this.loading = false;
          return;
        }

        this.Timer = this.timerStart();
      }
    },
    cancelDrop() {
      this.$refs['my-modal'].hide();
      this.loading = false;
    },
    timerStart() {
      // 1초에 한번씩 start 호출
      this.TimeCounter = 180;
      var interval = setInterval(() => {
        this.TimeCounter--; // 1초씩 감소
        this.TimerStr = this.prettyTime();
        if (this.TimeCounter <= 0 && this.Timer != null) {
          this.timerStop(interval);
        }
      }, 1000);
      return interval;
    },
    timerStop(Timer) {
      clearInterval(Timer);
      this.Timer = null;
      this.TimeCounter = 0;
      alert('인증 제한 시간이 만료되었습니다. 😥');
      this.$refs['my-modal'].hide();
      this.loading = false;
    },
    prettyTime() {
      // 시간 형식으로 변환 리턴
      let time = this.TimeCounter / 60;
      let minutes = parseInt(time);
      let secondes = Math.round((time - minutes) * 60);
      return (
        minutes.toString().padStart(2, '0') +
        ':' +
        secondes.toString().padStart(2, '0')
      );
    },
    async submit() {
      try {
        await confirmAuthCode(this.authCode);
        clearInterval(this.Timer);
        this.Timer = null;
        this.TimeCounter = 0;
        alert('회원 탈퇴가 정상적으로 처리되었습니다.');
        this.$refs['my-modal'].hide();
        this.$store.commit('LOGOUT');
        this.$router.push('/');
      } catch (error) {
        clearInterval(this.Timer);
        this.Timer = null;
        this.TimeCounter = 0;
        alert('올바르지 않은 인증 코드입니다.');
        this.$refs['my-modal'].hide();
        this.loading = false;
      }
    },
    cancelAuth() {
      clearInterval(this.Timer);
      this.Timer = null;
      this.TimeCounter = 0;
      alert('회원 탈퇴를 취소합니다. 😀');
      this.$refs['my-modal'].hide();
      this.loading = false;
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
  width: auto;
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

.form-input {
  display: inline;
  width: 260px;
  /* Set the desired width */
  margin-right: 10px;
}
</style>
