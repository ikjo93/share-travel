<template lang="">
  <div>
    <!-- TODO: primary key 사용해서 상세보기 만들어보기 -->
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
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      perPage: 10,
      currentPage: 1,
      fields: [{ key: 'title' }, 'author', 'writeDate', 'boardType'],
      items: [
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
        {
          title: '임시제목',
          author: '오승기',
          writeDate: 'yyyy-mm-dd',
          boardType: '자유',
          secret: '안보여야해',
        },
      ],
    };
  },
  methods: {
    moveDetail() {
      console.log('클릭 되냐 ?');
      this.$router.push({ name: 'boarddetail' });
    },
  },
  computed: {
    rows() {
      return this.items.length;
    },
  },
};
</script>
<style lang=""></style>
