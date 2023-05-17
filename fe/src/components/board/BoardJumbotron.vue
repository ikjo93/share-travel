<template>
  <div>
    <b-jumbotron
      header="커뮤니티"
      lead="여러분만의 여행 팁을 공유해주세요 !"
      bg-variant="white"
    >
      <button class="radious" @click="moveNotice()">공지사항</button>
      <button class="radious" @click="moveEvent()">이벤트</button>
      <button class="radious" @click="moveGeneral()">자유 게시판</button>
      <button class="radious" @click="moveTip()">꿀팁 게시판</button>
      <button class="radious" @click="moveQna()">QnA</button>
    </b-jumbotron>
  </div>
</template>
<script>
export default {
  data: function() {
    return {};
  },
  methods: {
    moveNotice() {
      // TODO:클릭한 버튼에 대해서 검은색 처리 및 나머지 버튼 색상해제
      this.$router.push({ name: 'boardnotice' });
    },
    moveEvent() {
      this.$router.push({ name: 'boardevent' });
    },
    moveGeneral() {
      this.$router.push({ name: 'boardgeneral' });
    },
    moveTip() {
      this.$router.push({ name: 'boardtip' });
    },
    moveQna() {
      this.$router.push({ name: 'boardqna' });
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
</style>
