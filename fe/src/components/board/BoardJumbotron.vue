<template>
  <div>
    <b-jumbotron
      header="커뮤니티"
      lead="여러분만의 여행 팁을 공유해주세요 !"
      bg-variant="white"
    >
      <button
        :class="['radious', { clickedBtn: isClicked[0] }]"
        @click="moveNotice(0)"
      >
        공지사항
      </button>
      <button
        :class="['radious', { clickedBtn: isClicked[1] }]"
        @click="moveEvent(1)"
      >
        이벤트
      </button>
      <button
        :class="['radious', { clickedBtn: isClicked[2] }]"
        @click="moveGeneral(2)"
      >
        자유 게시판
      </button>
      <button
        :class="['radious', { clickedBtn: isClicked[3] }]"
        @click="moveTip(3)"
      >
        꿀팁 게시판
      </button>
    </b-jumbotron>
  </div>
</template>
<script>
export default {
  data: function() {
    return {
      isClicked: [true, false, false, false],
    };
  },
  created() {
    this.moveNotice(0);
  },
  methods: {
    moveNotice(idx) {
      // TODO:클릭한 버튼에 대해서 검은색 처리 및 나머지 버튼 색상해제
      this.isClicked.fill(false);
      this.$set(this.isClicked, idx, true);
      this.$router.push({ name: 'boardnotice' });
    },
    moveEvent(idx) {
      this.isClicked.fill(false);
      this.$set(this.isClicked, idx, true);
      this.$router.push({ name: 'boardevent' });
    },
    moveGeneral(idx) {
      this.isClicked.fill(false);
      this.$set(this.isClicked, idx, true);
      this.$store.commit('SET_CATEGORY_ID', 1);
      this.$router.push({ name: 'boardgeneral' });
    },
    moveTip(idx) {
      this.isClicked.fill(false);
      this.$set(this.isClicked, idx, true);
      this.$router.push({ name: 'boardtip' });
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
  background-color: buttonface;
}
.clickedBtn {
  background-color: black;
  color: white;
}
</style>
