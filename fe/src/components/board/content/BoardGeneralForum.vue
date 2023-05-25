<template lang="">
  <div>
    <b-jumbotron
      header="커뮤니티"
      lead="여러분만의 여행 팁을 공유해주세요 !"
      bg-variant="white"
    >
      <button :class="['radious', 'clickedBtn']">
        자유 게시판
      </button>
      <button :class="['radious']" @click="moveCategory(1)">
        꿀팁 게시판
      </button>
      <button :class="['radious']" @click="moveCategory(2)">
        공지사항
      </button>
      <button :class="['radious']" @click="moveCategory(3)">
        이벤트
      </button>
    </b-jumbotron>
    <button
      :class="['radious', { changeColor: isHovering }]"
      id="writeBtn"
      @click="moveWrite()"
      @mouseover="hover()"
      @mouseleave="hover()"
    >
      글쓰기
    </button>
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
      fields: [
        { key: 'title', label: '제목', tdAttrs: { style: 'width: 70%' } },
        { key: 'nickName', label: '작성자', tdAttrs: { style: 'width: 30%' } },
      ],
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
  async created() {
    this.categoryId = await this.$store.state.categoryId;
    await this.getBoardList();
  },
  mounted() {
    this.selectType(0, 'title');
  },
  methods: {
    async getBoardList() {
      await getListByCategory(this.categoryId).then(boards => {
        this.boards = boards.data;
        this.createList();
        this.$nextTick(() => {
          this.items = [...this.items];
        });
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
          this.boards = [];
          this.items = [];
          this.boards = response.data;
          this.createList();
          this.$nextTick(() => {
            this.items = [...this.items];
          });
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
<style scoped>
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
