<template>
  <div>
    <b-navbar class="flex-column">
      <b-nav pills vertical>
        <b-nav-item href="#">공지사항</b-nav-item>
        <b-nav-item href="#">이벤트</b-nav-item>
        <b-nav-item href="#" v-b-toggle.collapse_community>커뮤니티</b-nav-item>
        <!-- TODO: 이 부분 클릭시 하위 탭이 나오게하기 -->
        <b-collapse :id="collap_community">
          <b-nav pills vertical>
            <b-nav-item class="ml-3 my-1" href="#">자유 게시판</b-nav-item>
            <b-nav-item class="ml-3 my-1" href="#">꿀팁 게시판</b-nav-item>
          </b-nav>
        </b-collapse>
        <b-nav-item href="#">테스트</b-nav-item>
      </b-nav>
    </b-navbar>
  </div>
</template>
<script>
export default {
  data() {
    return {
      collap_community: 'collapse_community',
    };
  },
};
</script>
<style>
.b-navbar {
  background-color: blueviolet;
}
</style>
