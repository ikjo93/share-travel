<template>
  <div>
    <div class="btn-box-top">
      <button class="radious temp" @click="boardModify()">수정</button>
      <button class="radious temp" @click="boardDelete()">삭제</button>
    </div>
    <section class="article-detail">
      <div class="article-detail-header">
        <div class="article-detail-meta">
          {{ article.boardType }} | {{ article.writeDate }}
        </div>
        <div class="article-detail-title">
          <h1>{{ article.title }}</h1>
        </div>
        <div class="article-detail-subtitle">
          {{ article.subTitle }}
        </div>
      </div>
      <div class="article-detail-content">
        {{ article.content }}
      </div>
    </section>
    <div class="btn-box">
      <button class="radious" @click="moveGeneral()">
        목록으로
      </button>
    </div>
    <section class="article-reply">
      <div class="article-reply-add">
        <textarea
          class="article-reply-textarea"
          placeholder="기분 좋은 리뷰는 작성자를 춤추게 합니다 !"
        ></textarea>
      </div>
      <div class="article-reply-list">
        <div v-if="replies.length">
          <div
            v-for="(reply, index) in replies"
            :key="index"
            class="article-replies"
          >
            <div class="article-replies-author">
              {{ reply.reAuthor }}
            </div>
            {{ reply.reContent }}
          </div>
        </div>
        <div v-else></div>
      </div>
    </section>
  </div>
</template>
<script>
export default {
  data() {
    return {
      article: {
        title: '큰제목이다ㅏ아아아아ㅏ아아',
        subTitle: '소제목인데엥?',
        boardType: '자유',
        writeDate: 'yyyy-mm-dd',
        content:
          "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
      },
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
  methods: {
    moveGeneral() {
      this.$router.push({ name: 'boardgeneral' });
    },
    boardModify() {
      console.log('수정버튼');
    },
    boardDelete() {
      console.log('삭제버튼');
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
  width: fit-content;
  margin: auto;
}
.btn-box-top {
  float: right;
}
.btn-box-top > button {
  display: inline;
}
.btn-box {
  padding-top: 40px;
}
.article-detail-header,
.article-detail-content {
  border-bottom: 1px solid #d6d7da;
  padding: 40px;
}
.article-reply-add {
  padding: 40px;
  border-bottom: 1px solid #d6d7da;
}
.article-reply-textarea {
  font-weight: 400;
  line-height: 1.47;
  letter-spacing: -0.3px;
  font-size: 15px;
  padding: 13px 12px;
  width: 100%;
  min-height: 102px;
  border: none;
  background-color: #fff;
  color: #495057;
  resize: none;
}
.article-replies-author {
  padding: 40px;
}
</style>
