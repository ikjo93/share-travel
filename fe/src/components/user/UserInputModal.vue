<template>
  <div>
    <b-modal
      ref="my-modal"
      hide-header
      hide-footer
      no-close-on-esc
      no-close-on-backdrop
      hide-header-close
    >
      <b-jumbotron
        lead="서비스간 이용할 귀하의 정보를 입력해주세요 💁‍♂️"
        bg-variant="white"
        style="font-family: 'hanna-pro';"
      >
        <b-form-group
          id="fieldset-1"
          label="귀하의 닉네임을 입력해주세요. 😁"
          label-for="input-1"
          valid-feedback="확인되었습니다. 🎉"
          :invalid-feedback="invalidNickNameFeedback"
          :state="nickNameState"
        >
          <b-form-input
            id="input-1"
            v-model="userInputNickName"
            :state="nickNameState"
            trim
            class="form-input"
            @keyup="duplicated = true"
          ></b-form-input>
          <b-button
            variant="outline-primary"
            :disabled="!nickNameState || !duplicated"
            @click="checkNickName"
          >
            <span v-if="duplicated">중복 확인하기</span>
            <span v-else style="color: #28a745">중복 확인 완료!</span>
          </b-button>
        </b-form-group>
        <b-form-group
          id="fieldset-1"
          label="선호하시는 여행 키워드 3가지를 선택해주세요. 💕"
          label-for="input-1"
          valid-feedback="확인되었습니다. 🎉"
          :invalid-feedback="invalidtravelKeywordFeedback"
          :state="travelKeywordState"
        >
          <div class="grid-container">
            <button
              class="radious grid-item"
              v-for="travelKeyword in travelKeywords"
              :key="travelKeyword.id"
              @click="selectTravelKeyword(travelKeyword)"
              :value="travelKeyword.id"
              :class="{ selected: travelKeyword.selected }"
            >
              {{ travelKeyword.name }}
            </button>
          </div>
        </b-form-group>
        <div class="button-container">
          <b-button
            :disabled="!(nickNameState && !duplicated && travelKeywordState)"
            size="lg"
            :class="{
              'custom-button':
                nickNameState && !duplicated && travelKeywordState,
              disabled: !(nickNameState && !duplicated && travelKeywordState),
            }"
            @click="submit"
            >확인</b-button
          >
        </div>
      </b-jumbotron>
    </b-modal>
  </div>
</template>

<script>
import { getTravelKeywords } from '@/api/travel.js';
import { validateUserNickName, registerUser } from '@/api/user.js';
import { deleteCookie } from '@/utils/cookies';

export default {
  data() {
    return {
      travelKeywords: [],
      userInputNickName: '',
      userInputTravelKeywords: [],
      duplicated: true,
    };
  },
  computed: {
    nickNameState() {
      return (
        this.userInputNickName.length >= 6 &&
        this.userInputNickName.length <= 30
      );
    },
    invalidNickNameFeedback() {
      if (this.nickNameState) {
        return '';
      } else {
        return '최소 6자, 최대 15 자로 작성해주세요.';
      }
    },
    travelKeywordState() {
      return (
        this.userInputTravelKeywords.length > 0 &&
        this.userInputTravelKeywords.length < 4
      );
    },
    invalidtravelKeywordFeedback() {
      if (this.travelKeywordState) {
        return '';
      } else {
        return '최소 1개, 최대 3개를 선택해주세요.';
      }
    },
  },
  methods: {
    selectTravelKeyword(keyword) {
      keyword.selected = !keyword.selected;
      if (keyword.selected) {
        this.userInputTravelKeywords.push(keyword.id);
      } else {
        this.deleteTravelKeyword(keyword);
      }
    },
    deleteTravelKeyword(keyword) {
      const idx = this.userInputTravelKeywords.indexOf(keyword.id);
      if (idx > -1) {
        this.userInputTravelKeywords.splice(idx, 1);
      }
    },
    async checkNickName() {
      const { data } = await validateUserNickName(this.userInputNickName);
      if (data.code === 'U03') {
        this.duplicated = true;
        alert(data.message);
      } else if (data.code == 'U04') {
        this.duplicated = false;
      } else {
        alert(data.message);
      }
    },
    async submit() {
      const body = {
        nickName: this.userInputNickName,
        travelKeywords: this.userInputTravelKeywords,
      };
      try {
        await registerUser(body);
        alert('회원 정보가 정상적으로 처리되었습니다. 🎉');
        this.$refs['my-modal'].hide();
      } catch (error) {
        alert('회원 정보를 등록하는 과정에서 에러가 발생했습니다. 😢');
        this.processLogout();
      }
    },
    processLogout() {
      this.$store.commit('LOGOUT');
      if (this.$route.path !== '/') {
        this.$router.push('/');
      }
      deleteCookie('renew');
    },
  },
  mounted() {
    this.$refs['my-modal'].show();
  },
  async created() {
    const { data } = await getTravelKeywords();
    for (let i = 0; i < data.length; i++) {
      this.travelKeywords.push({
        id: data[i].id,
        name: data[i].name,
        selected: false,
      });
    }
  },
};
</script>

<style scoped>
.radious {
  border-radius: 50px !important;
  padding: 5px 12px !important;
  overflow-x: auto !important;
  margin: 5px !important;
  white-space: nowrap !important;
  border: 0px !important;
}
.grid-container {
  display: flex;
  flex-wrap: wrap;
}
.grid-container > .grid-item {
  flex: 0 0 calc(33.33% - 10px);
  margin: 5px;
  border: 1px solid #ccc;
  font-size: 25px;
  text-align: center;
}
.grid-container > .grid-item.selected {
  background-color: #ffcc00; /* Update with desired background color */
  color: #ffffff; /* Update with desired letter color */
}

.button-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 50px; /* Adjust the margin as needed */
}

.custom-button {
  background-color: #28a745; /* Update with desired background color */
  color: #ffffff; /* Update with desired text color */
}

.custom-button.disabled {
  background-color: #dc3545; /* Update with desired background color */
  color: #ffffff; /* Update with desired text color */
}

.form-input {
  display: inline;
  width: 260px; /* Set the desired width */
  margin-right: 10px;
}
</style>
