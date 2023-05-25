<template>
  <div>
    <div class="btn-box-top">
      <div class="btn-box-right" v-if="isSameName()">
        <button class="radious" @click="boardModify()">수정</button>
        <button class="radious" @click="boardDelete()">삭제</button>
      </div>
    </div>
    <section class="article-detail">
      <div class="article-detail-header">
        <div class="article-detail-meta">
          {{ categories[board.categoryId - 1] }}
        </div>
        <div class="article-detail-title">
          <h1>{{ board.title }}</h1>
        </div>
        <div class="article-detail-subtitle">
          {{ board.subTitle }}
        </div>
      </div>
      <div class="article-detail-content">
        {{ board.content }}
      </div>
    </section>
    <div class="btn-box">
      <button class="radious" @click="moveGeneral()">
        목록으로
      </button>
    </div>
  </div>
</template>
<script>
import { getDetail, deleteBoard } from '@/api/board';

export default {
  data() {
    return {
      board: {
        categoryId: '',
        boardId: '',
        nickName: '',
        title: '',
        subTitle: '',
        content: '',
      },
      categories: ['자유게시판', '꿀팁게시판', '공지사항', '이벤트'],
      replies: [
        {
          reAuthor: '오승기1',
          reContent: '좋아요 좋아요 너무 좋아요',
        },
        {
          reAuthor: '오승기2',
          reContent: '좋아요 좋아요 너무 좋아요',
        },
        {
          reAuthor: '오승기3',
          reContent: '좋아요 좋아요 너무 좋아요',
        },
      ],
    };
  },
  async created() {
    this.board.boardId = this.$route.query.boardId;
    await getDetail(this.$route.query.boardId).then(response => {
      this.board = response.data;
      this.board.categoryId = this.$store.state.categoryId;
    });
  },
  methods: {
    isSameName() {
      if (this.$store.state.user.nickName == this.board.nickName) {
        console.log('정상');
        return true;
      } else {
        console.log('비정상');
        return false;
      }
    },
    moveGeneral() {
      this.$router.push({ name: 'boardgeneral' });
    },
    boardModify() {
      if (confirm('수정하시겠습니까 ?')) {
        this.$router.push({
          name: 'boardmodify',
          query: { boardId: this.board.boardId },
        });
      }
    },
    boardDelete() {
      if (confirm('정말 삭제하시겠습니까 ?')) {
        deleteBoard(this.board.boardId).then(
          () => alert('삭제가 완료되었습니다'),
          this.$router.push({ name: 'boardgeneral' }),
        );
      }
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
  display: block;
}
div:not(.btn-box-top) {
  /* width: fit-content; */
  /* margin: auto; */
}
.btn-box-top {
  display: flex;
  justify-content: flex-end;
  margin: 50px;
}
.btn-box-top > .btn-box-right {
  display: flex;
}
.btn-box-top > .btn-box-right > button {
  margin-left: 10px;
}
.btn-box {
  padding-top: 40px;
  width: fit-content;
  margin: auto;
}
.article-detail {
  width: 800px; /* 원하는 가로 넓이로 조정 */
  margin: 0 auto;
}
.article-detail-header,
.article-detail-content {
  width: 100%;
  border-bottom: 1px solid #d6d7da;
  padding: 40px;
}
.article-detail-meta,
.article-detail-title,
.article-detail-subtitle,
.article-detail-content {
  width: fit-content;
  margin: auto;
}
.article-detail-content {
  width: 800px; /* 원하는 가로 크기로 설정 */
  word-wrap: break-word; /* 줄 바꿈 가능하도록 설정 */
  margin: 0 auto; /* 수평 가운데 정렬 */
  text-align: center; /* 텍스트 가운데 정렬 */
}
</style>
