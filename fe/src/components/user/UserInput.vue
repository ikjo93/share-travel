<template>
  <div>
    <b-modal
      ref="my-modal"
      hide-footer
      no-close-on-esc
      no-close-on-backdrop
      hide-header-close
    >
      <b-jumbotron
        lead="서비스 이용을 위한 정보를 입력해주세요! 🙇‍♂️"
        bg-variant="white"
        style="font-family: 'hanna-pro';"
      >
        <b-form-group
          id="fieldset-1"
          label="닉네임을 입력해주세요."
          label-for="input-1"
          valid-feedback="Thank you!"
          :invalid-feedback="invalidFeedback"
          :state="state"
        >
          <b-form-input
            id="input-1"
            v-model="userInputNickName"
            :state="state"
            trim
          ></b-form-input>
        </b-form-group>
        <div v-for="travelKeyword in travelKeywords" :key="travelKeyword">
          <button class="radious" @click="addTravelKeyword()">
            {{ travelKeyword.name }}
          </button>
        </div>
      </b-jumbotron>
    </b-modal>
  </div>
</template>

<script>
import { getTravelKeywords } from '@/api/travel.js';

export default {
  computed: {
    state() {
      return (
        this.userInputNickName.length >= 6 &&
        this.userInputNickName.length <= 30
      );
    },
    invalidFeedback() {
      if (this.userInputNickName.length > 0) {
        return '최소 6자, 최대 30 자로 작성해주세요.';
      }
      return '최소 6자, 최대 30 자로 작성해주세요.';
    },
  },
  methods: {
    addTravelKeyword() {
      console.log('todo');
    },
  },
  mounted() {
    this.$refs['my-modal'].show();
  },
  data() {
    return {
      travelKeywords: [],
      userInputNickName: '',
      userInputTravelKeywords: [],
    };
  },
  created() {
    this.userInputTravelKeywords = getTravelKeywords();
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
</style>
