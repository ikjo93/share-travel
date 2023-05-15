<template>
  <div>
    <b-container class="bv-example-row">
      <b-jumbotron
        header="커뮤니티"
        lead="여러분만의 여행 팁을 공유해주세요 !"
        bg-variant="white"
      >
        <!-- <img src="../../public/light.png" /> -->
        <button>공지사항</button>
        <button>이벤트</button>
        <button>자유 게시판</button>
        <button>꿀팁 게시판</button>
      </b-jumbotron>
      <b-row>
        <b-col cols="2" md="auto" :style="'background-color:blueviolet'">
          <board-side-bar></board-side-bar>
        </b-col>
        <b-col cols="10" md="auto">
          <board-content></board-content>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import BoardSideBar from '../components/common/board/BoardSideBar.vue';
import BoardContent from '../components/common/board/BoardContent.vue';
export default {
  components: {
    BoardSideBar,
    BoardContent,
  },
};
</script>

<style>
button {
  border-radius: 50px;
  padding: 5px 12px;
  overflow-x: auto;
  margin: 5px;
  white-space: nowrap;
}
</style>
