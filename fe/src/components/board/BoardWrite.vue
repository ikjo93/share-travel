<template>
  <div>
    <div class="board-write-tab">
      <b-input type="text" name="title" placeholder="대제목" />
      <b-input type="text" name="subTitle" placeholder="소제목" />
      <b-form-textarea
        id="textarea-rows"
        placeholder="게시판 작성창"
        rows="35"
        no-resize
      ></b-form-textarea>
    </div>
    <div class="board-write-btn">
      <button class="radious" @click="redirectBoard()">취소</button>
      <button class="radious" @click="boardRegist()">등록</button>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      categoryId: '',
      nickName: '',
    };
  },
  computed: {},
  created() {
    this.categoryId = this.$store.state.categoryId;
    this.nickName = this.$store.state.user.nickName;
  },
  methods: {
    boardRegist() {
      console.log('등록버튼 클릭함');
    },
    redirectBoard() {
      console.log('취소버튼 클릭함');
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
