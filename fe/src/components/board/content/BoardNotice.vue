<template lang="">
  <div>
    <b-jumbotron
      header="커뮤니티"
      lead="여러분만의 여행 팁을 공유해주세요 !"
      bg-variant="white"
    >
      <button :class="['radious']" @click="moveCategory(0)">
        자유 게시판
      </button>
      <button :class="['radious']" @click="moveCategory(1)">
        꿀팁 게시판
      </button>
      <button :class="['radious', 'clickedBtn']">
        공지사항
      </button>
      <button :class="['radious']" @click="moveCategory(3)">
        이벤트
      </button>
    </b-jumbotron>
    <div v-if="false"></div>
    <div v-else class="no-content">
      <img src="../../../../public/icon_noresult.png" />
      <p>아직 고..곰지사항이 없어요 !</p>
    </div>
  </div>
</template>
<script>
export default {
  methods: {
    moveCategory(idx) {
      if (idx == 0) {
        this.$store.commit('SET_CATEGORY_ID', 1);
        this.$router.push({ name: 'boardgeneral' });
      } else if (idx == 1) {
        this.$store.commit('SET_CATEGORY_ID', 2);
        this.$router.push({ name: 'boardtip' });
      } else if (idx == 2) {
        this.$store.commit('SET_CATEGORY_ID', 3);
        this.$router.push({ name: 'boardnotice' });
      } else if (idx == 3) {
        this.$store.commit('SET_CATEGORY_ID', 4);
        this.$router.push({ name: 'boardevent' });
      }
    },
  },
};
</script>
<style scoped>
.no-content {
  width: fit-content;
  margin: auto;
  padding-top: 60px;
}
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
