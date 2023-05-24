<template lang="">
  <div>
    <button
      :class="['radious', { changeColor: isHovering }]"
      id="writeBtn"
      @click="moveWrite()"
      @mouseover="hover()"
      @mouseleave="hover()"
    >
      글쓰기
    </button>
    <button @click="getLog()">로그용</button>
    <div v-if="items.length">
      <div class="article-search-input">
        <div calss="article-search-type">
          <button
            :class="['radious', 'searchTypeBtn', { clickedBtn: isClicked[0] }]"
            @click="selectType(0, 'title')"
          >
            제목
          </button>
          <button
            :class="['radious', 'searchTypeBtn', { clickedBtn: isClicked[1] }]"
            @click="selectType(1, 'author')"
          >
            작성자
          </button>

          <label>
            <input
              id="search-input"
              placeholder="검색어를 입력하세요"
              v-model="keyword"
              @keyup.enter="submit()"
            />
            <button type="button" class="radious" @click="submit()">
              <img id="submitImage" src="../../../../public/search_icon.png" />
            </button>
          </label>
        </div>
      </div>
      <b-table
        id="articleTable"
        samll
        hover
        :items="items"
        :fields="fields"
        :per-page="perPage"
        :current-page="currentPage"
        @row-clicked="moveDetail"
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
      <p>글이 없졍.. 글 좀 써주랑..</p>
    </div>
  </div>
</template>
<script>
import { getListByCategory, getListByCondition } from '@/api/board.js';

export default {
  data() {
    return {
      perPage: 10,
      currentPage: 1,
      fields: ['boardId', 'title', 'nickName'],
      items: [],
      item: {
        boardId: '',
        categoryId: '',
        nickName: '',
        subTitle: '',
        title: '',
      },
      boards: [],
      isHovering: false,
      categoryId: '',
      isClicked: [true, false],
      searchType: null,
      keyword: null,
    };
  },
  created() {
    this.categoryId = this.$store.state.categoryId;
    this.getBoardList();
  },
  mounted() {
    this.selectType(0, 'title');
  },
  methods: {
    getLog() {
      console.log(this.board);
    },
    async getBoardList() {
      await getListByCategory(this.categoryId).then(boards => {
        this.boards = boards.data;
        this.createList();
      });
    },
    createList() {
      for (var i = 0; i < this.boards.length; i++) {
        this.item.boardId = this.boards[i].boardId;
        this.item.categoryId = this.boards[i].categoryId;
        this.item.nickName = this.boards[i].nickName;
        this.item.subTitle = this.boards[i].subTitle;
        this.item.title = this.boards[i].title;
        this.items.push(this.item);
        this.item = {};
      }
    },
    moveDetail(item) {
      this.$router.push({
        name: 'boarddetail',
        query: { boardId: item.boardId },
      });
    },
    moveWrite() {
      this.$router.push({ name: 'boardwrite' });
    },
    hover() {
      this.isHovering = !this.isHovering;
    },
    selectType(idx, type) {
      this.isClicked.fill(false);
      this.$set(this.isClicked, idx, true);
      this.searchType = type;
    },
    async submit() {
      if (this.keyword != null) {
        await getListByCondition(
          this.categoryId,
          this.searchType,
          this.keyword,
        ).then(response => {
          this.board = response.data;
          this.createList;
        });
      } else {
        alert('검색어를 입력하세요 !');
      }
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
.clickedBtn {
  background-color: black;
  color: white;
}
</style>
