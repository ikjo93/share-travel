<template lang="">
  <div>
    <div>
      <button class="radious" @click="moveWrite()">글쓰기</button>
    </div>
    <div v-if="items.length">
      <b-table
        id="articleTable"
        samll
        hover
        :items="items"
        :fields="fields"
        :per-page="perPage"
        :current-page="currentPage"
        @row-clicked="moveDetail()"
      >
      </b-table>
      <!-- https://bootstrap-vue.org/docs/components/pagination#component-reference 꾸밀때 참고 -->
      <b-pagination
        v-model="currentPage"
        :total-rows="rows"
        :per-page="perPage"
        aria-controls="articleTable"
        first-text="⏮"
        prev-text="⏪"
        next-text="⏩"
        last-text="⏭"
        class="mt-4"
        align="center"
      ></b-pagination>
    </div>
    <div v-else>
      <img src="../../../../public/icon_noresult.png" />
      <p>글이 없졍..</p>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      perPage: 10,
      currentPage: 1,
      fields: ['title', 'author', 'writeDate', 'boardType'],
      items: [],
    };
  },
  methods: {
    moveDetail() {
      this.$router.push({ name: 'boarddetail' });
    },
    moveWrite() {
      this.$router.push({ name: 'boardwrite' });
    },
  },
  computed: {
    rows() {
      return this.items.length;
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
