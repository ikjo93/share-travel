<template>
  <div>
    <b-button
      v-b-toggle.sidebar-1
      class="position-fixed top-0 left-0 mt-3 ml-3"
      style="z-index: 1050;"
      :variant="searchBarFlag"
      @click="toggle"
    >
      <div v-if="searchBar">여행지 검색창 닫기</div>
      <div v-else>여행지 검색창 열기</div>
    </b-button>
    <b-sidebar
      id="sidebar-1"
      shadow
      title="원하시는 여행지를 검색해보세요! 📢"
      width="25%"
    >
      <div class="px-3 py-2" style="margin-top: 15%;">
        <b-nav-form>
          <b-form-input
            size="lg"
            class="mr-sm-2"
            placeholder="현재 위치에서 찾으실 여행지를 작성해주세요"
          ></b-form-input>
          <b-button size="lg" class="my-2 my-sm-0" type="submit">검색</b-button>
        </b-nav-form>
        <b-list-group style="margin-top: 20px;">
          <b-list-group-item>Cras justo odio</b-list-group-item>
          <b-list-group-item>Dapibus ac facilisis in</b-list-group-item>
          <b-list-group-item>Morbi leo risus</b-list-group-item>
          <b-list-group-item>Porta ac consectetur ac</b-list-group-item>
          <b-list-group-item>Vestibulum at eros</b-list-group-item>
        </b-list-group>
      </div>
    </b-sidebar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchBar: false,
    };
  },
  computed: {
    searchBarFlag() {
      return this.searchBar ? 'danger' : 'primary';
    },
  },
  methods: {
    toggle() {
      this.searchBar = !this.searchBar;
    },
  },
};
</script>

<style></style>
