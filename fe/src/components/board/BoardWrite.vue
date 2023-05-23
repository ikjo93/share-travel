<template>
  <div>
    <div class="board-write-tab">
      <b-input
        ref="title"
        type="text"
        placeholder="대제목"
        v-model="board.title"
      />
      <b-input
        ref="subTitle"
        type="text"
        placeholder="소제목"
        v-model="board.subTitle"
      />
      <b-form-textarea
        ref="textarea"
        id="textarea-rows"
        placeholder="게시판 작성창"
        rows="35"
        no-resize
        v-model="board.content"
      ></b-form-textarea>
    </div>
    <div class="board-write-btn">
      <button class="radious" @click="redirectBoard()">취소</button>
      <button class="radious" @click="regist()">등록</button>
    </div>
  </div>
</template>
<script>
import { registBoard } from '@/api/board.js';

export default {
  data() {
    return {
      board: {
        categoryId: '',
        nickName: '',
        title: null,
        subTitle: null,
        content: null,
      },
    };
  },
  computed: {},
  created() {
    this.board.categoryId = this.$store.state.categoryId;
    this.board.nickName = this.$store.state.user.nickName;
  },
  methods: {
    regist() {
      let err = true;
      let msg = '';
      !this.board.title &&
        ((msg = '대제목을 입력해주세요'),
        (err = false),
        this.$refs.title.focus());
      err &&
        !this.board.subTitle &&
        ((msg = '소제목을 입력해주세요'),
        (err = false),
        this.$refs.subTitle.focus());
      err &&
        !this.board.content &&
        ((msg = '내용을 입력해주세요'),
        (err = false),
        this.$refs.textarea.focus());

      if (!err) alert(msg);
      else
        registBoard({
          categoryId: this.board.categoryId,
          nickName: this.board.nickName,
          title: this.board.title,
          subTitle: this.board.subTitle,
          content: this.board.content,
        });
    },
    redirectBoard() {
      if (confirm('취소하면 작성한 모든 내용이 사라집니다 !')) {
        if (this.categoryId == 1) {
          this.$router.push({ name: 'boardgeneral' });
        } else {
          this.$router.push({ name: 'boardtip' });
        }
      }
    },
  },
};
</script>
<style>
.radious {
  border-radius: 50px !important;
  padding: 5px 12px !important;
  overflow-x: auto !important;
  margin: 5px !important;
  white-space: nowrap !important;
  border: 0px !important;
}
.board-write-btn {
  padding-top: 20px;
  width: fit-content;
  margin: auto;
}
</style>
