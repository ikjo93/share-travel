<template lang="">
  <div>
    <div calss="article-search-type">
      <button class="radious searchTypeBtn">제목</button>
      <button class="radious searchTypeBtn">제목+작성자</button>
      <button class="radious searchTypeBtn">작성자</button>
      <button
        :class="['radious', { changeColor: isHovering }]"
        id="writeBtn"
        @click="moveWrite()"
        @mouseover="hover()"
        @mouseleave="hover()"
      >
        글쓰기
      </button>
    </div>
    <div class="article-search-input">
      <label>
        <input id="search-input" />
        <button type="button" class="radious">
          <img id="submitImage" src="../../../../public/search_icon.png" />
        </button>
      </label>
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
    <div v-else class="no-content">
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
      isHovering: false,
    };
  },
  methods: {
    moveDetail() {
      this.$router.push({ name: 'boarddetail' });
    },
    moveWrite() {
      this.$router.push({ name: 'boardwrite' });
    },
    hover() {
      this.isHovering = !this.isHovering;
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
#writeBtn {
  float: right;
}
.searchTypeBtn {
  float: left;
}
#search-input {
  border-radius: 50px !important;
  padding: 2.5px 12px !important;
  overflow-x: auto !important;
  margin: 5px !important;
  white-space: nowrap !important;
  outline: none;
}
#submitImage {
  width: 10px;
  height: 10px;
}
.no-content {
  width: fit-content;
  margin: auto;
  padding-top: 60px;
}
.changeColor {
  background-color: black;
  color: white;
}
</style>
