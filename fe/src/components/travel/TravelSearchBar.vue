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
      no-header-close
    >
      <div class="px-3 py-2" style="margin-top: 15%;">
        <b-form-group
          id="fieldset-1"
          label="선호하시는 여행 키워드로 검색해보세요. 💕"
          label-for="input-1"
        >
          <div class="grid-container">
            <button
              class="radious grid-item"
              v-for="travelKeyword in travelKeywords"
              :key="travelKeyword.id"
              @click="selectTravelKeyword(travelKeyword)"
              :value="travelKeyword.id"
              :class="{ selected: travelKeyword.selected }"
            >
              {{ travelKeyword.name }}
            </button>
          </div>
        </b-form-group>
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
import { getTravelKeywords } from '@/api/travel.js';

export default {
  data() {
    return {
      searchBar: false,
      travelKeywords: [],
      userInputTravelKeywords: [],
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
    async selectTravelKeyword(keyword) {
      keyword.selected = !keyword.selected;
      if (keyword.selected) {
        this.userInputTravelKeywords.push(keyword.id);
      } else {
        this.deleteTravelKeyword(keyword);
      }
    },
  },
  async created() {
    const { data } = await getTravelKeywords();
    for (let i = 0; i < data.length; i++) {
      this.travelKeywords.push({
        id: data[i].id,
        name: data[i].name,
        selected: false,
      });
    }
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

.grid-container {
  display: flex;
  flex-wrap: wrap;
}

.grid-container > .grid-item {
  flex: 0 0 calc(33.33% - 10px);
  margin: 5px;
  border: 1px solid #ccc;
  font-size: 25px;
  text-align: center;
}

.grid-container > .grid-item.selected {
  background-color: #ffcc00;
  /* Update with desired background color */
  color: #ffffff;
  /* Update with desired letter color */
}

.button-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 50px;
  /* Adjust the margin as needed */
}
</style>
