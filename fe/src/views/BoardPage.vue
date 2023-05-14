<template>
  <div>
    <b-container class="bv-example-row">
      <b-row>
        <b-col cols="4">
          <board-side-bar></board-side-bar>
        </b-col>
        <b-col cols="8">
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

<style></style>
