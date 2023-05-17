<template>
  <div>
    <b-container class="bv-example-row">
      <board-jumbotron></board-jumbotron>
      <b-row>
        <b-col cols="12">
          <router-view></router-view>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import BoardJumbotron from '../components/board/BoardJumbotron.vue';
export default {
  name: 'board',
  components: {
    BoardJumbotron,
  },
};
</script>

<style></style>
