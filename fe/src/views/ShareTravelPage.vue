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
              v-for="travelKeyword in searchBarTravelKeywords"
              :key="travelKeyword.id"
              @click="selectSearchBarTravelKeyword(travelKeyword)"
              :value="travelKeyword.id"
              :class="{ selected: travelKeyword.selected }"
            >
              {{ travelKeyword.name }}
            </button>
          </div>
        </b-form-group>
        <div class="travel-list" v-for="info in travelInfo" :key="info.id">
          <div class="travel-container row">
            <div class="content">
              <h4>🚆 {{ info.name }}</h4>
              <span>⭐ {{ info.travelKeyword }} 키워드</span>
            </div>
            <div style="margin: auto;">
              <img
                src="/arrow.png"
                width="50px"
                height="50px"
                style="cursor: pointer;"
                @click="openTravelInfoModal(info.travelId)"
              />
            </div>
          </div>
        </div>
      </div>
    </b-sidebar>
    <div id="map"></div>
    <b-modal
      ref="registerTravelModal"
      hide-footer
      hide-header
      no-close-on-esc
      no-close-on-backdrop
    >
      <b-jumbotron
        lead="나만의 여행지 등록하기 💁‍♂️"
        bg-variant="white"
        style="font-family: 'hanna-pro';"
      >
        <b-form-group
          id="fieldset-1"
          label="등록하실 여행지의 이름을 입력해주세요. 😁"
          label-for="input-1"
          valid-feedback="확인되었습니다. 🎉"
          :invalid-feedback="invalidTravelNameFeedback"
          :state="travelNameState"
        >
          <b-form-input
            id="input-1"
            v-model="userInputTravelName"
            :state="travelNameState"
            trim
            class="form-input"
          ></b-form-input>
        </b-form-group>
        <b-form-group
          id="fieldset-1"
          label="등록하실 여행지에 대해 설명해주세요. 📢"
          label-for="input-1"
          valid-feedback="확인되었습니다. 🎉"
          :invalid-feedback="invalidTravelDescriptionFeedback"
          :state="travelDescriptionState"
        >
          <b-form-textarea
            id="textarea"
            v-model="userInputTravelDescription"
            :state="travelDescriptionState"
            placeholder="여행지에 대한 설명을 자유롭게 작성해주세요. 😁"
            rows="6"
            class="form-input"
          ></b-form-textarea>
        </b-form-group>
        <b-form-group
          id="fieldset-1"
          label="해당 여행지에 등록하실 키워드 하나를 선택해주세요. 💕"
          label-for="input-1"
          valid-feedback="확인되었습니다. 🎉"
          :invalid-feedback="invalidtravelKeywordFeedback"
          :state="travelKeywordState"
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
        <b-form-group
          id="fieldset-1"
          label="해당 여행지 관련 최소 1개 최대 3개의 사진을 등록해주세요. 🎞 (jpg, png 파일 및 최대 10MB)"
          label-for="input-1"
          valid-feedback="확인되었습니다. 🎉"
          :invalid-feedback="invalidTravelPictureFeedback"
          :state="travelPictureState"
          accept=".jpg, .png"
        >
          <b-form-file
            multiple
            v-model="userInputTravelPictures"
            :state="travelPictureState"
            placeholder="사진을 이곳에 등록해주세요. 💁‍♂️"
            drop-placeholder="이곳에 드래그하세요. 💁‍♂️"
          ></b-form-file>
        </b-form-group>
        <div class="button-container">
          <b-button
            :disabled="
              !(
                travelNameState &&
                travelKeywordState &&
                travelDescriptionState &&
                travelPictureState
              )
            "
            size="lg"
            :class="{
              'custom-button':
                travelNameState &&
                travelKeywordState &&
                travelDescriptionState &&
                travelPictureState,
              disabled: !(
                travelNameState &&
                travelKeywordState &&
                travelDescriptionState &&
                travelPictureState
              ),
            }"
            @click="submit"
            >등록하기</b-button
          >
          <b-button
            size="lg"
            variant="outline-danger"
            @click="close"
            style="margin-left: 5px;"
            >닫기</b-button
          >
        </div>
      </b-jumbotron>
    </b-modal>
    <b-modal ref="detailTravelModal" hide-footer hide-header>
      <div class="modal-content" style="font-family: 'hanna-pro';">
        <b-card no-body class="border-0">
          <b-card-body class="p-4">
            <h2>여행지 상세 정보 💁‍♂️</h2>
            <h3 class="custom-heading">여행지 이름</h3>
            <p>{{ detailTravelInfo.name }}</p>
            <h3 class="custom-heading">여행지 키워드</h3>
            <p>{{ detailTravelInfo.travelKeyword }}</p>
            <h3 class="custom-heading">작성자</h3>
            <p>{{ detailTravelInfo.writer }}</p>
            <h3 class="custom-heading">장소 설명</h3>
            <p>{{ detailTravelInfo.description }}</p>
            <h3 class="custom-heading">사진</h3>
            <div class="image-list">
              <div
                v-for="(url, index) in detailTravelInfo.urls"
                :key="index"
                class="image-item"
              >
                <img :src="url" class="image-preview" />
              </div>
            </div>
            <div class="button-container">
              <b-button
                size="lg"
                variant="outline-danger"
                @click="closeDetailTravelInfo"
                class="mr-2"
              >
                닫기
              </b-button>
            </div>
          </b-card-body>
        </b-card>
      </div>
    </b-modal>
  </div>
</template>

<script>
import {
  getTravelKeywords,
  getTravelInfoById,
  getTravelInfoAroundCoordinate,
  getTravelInfoAroundCoordinateByKeywordId,
  registerTravel,
} from '@/api/travel.js';

export default {
  name: 'KakaoMap',

  data() {
    return {
      searchBar: false,
      searchBarTravelKeywords: [],
      searchBarUserInputTravelKeywords: [],
      selectedTravelKeywordIdx: -1,
      map: null, // 카카오맵 지도 객체
      markers: [], // 카카오맵에 존재하는 마커 객체 배열
      infoWin: null, // 선택한 인포윈도우 객체
      travelKeywords: [],
      userInputTravelName: '',
      userInputTravelDescription: '',
      userInputTravelKeywords: [],
      userInputTravelPictures: [],
      longitude: 0,
      latitude: 0,
      travelInfo: [], // 검색한 여행지 정보 객체 배열
      detailTravelInfo: {}, // 상세화면 정보 객체
    };
  },
  computed: {
    searchBarFlag() {
      return this.searchBar ? 'danger' : 'primary';
    },
    travelNameState() {
      return (
        this.userInputTravelName.length >= 1 &&
        this.userInputTravelName.length <= 30
      );
    },
    invalidTravelNameFeedback() {
      if (this.travelNameState) {
        return '';
      } else {
        return '최소 1자, 최대 30 자로 작성해주세요.';
      }
    },
    travelKeywordState() {
      return this.userInputTravelKeywords.length == 1;
    },
    invalidtravelKeywordFeedback() {
      if (this.travelKeywordState) {
        return '';
      } else {
        return '하나의 여행지 키워드를 선택해주세요. 🙌';
      }
    },
    travelDescriptionState() {
      return (
        this.userInputTravelDescription.length >= 1 &&
        this.userInputTravelDescription.length <= 500
      );
    },
    invalidTravelDescriptionFeedback() {
      if (this.travelNameState) {
        return '';
      } else {
        return '최소 1자, 최대 500 자 이내로 작성해주세요.';
      }
    },
    travelPictureState() {
      return (
        this.userInputTravelPictures.length >= 1 &&
        this.userInputTravelPictures.length <= 3
      );
    },
    invalidTravelPictureFeedback() {
      if (this.travelPictureState) {
        return '';
      } else {
        return '최소 1개, 최대 3개의 사진 파일을 등록해주세요.';
      }
    },
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement('script');
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAOMAP_APP_KEY}`;
      document.head.appendChild(script);
    }
  },
  methods: {
    toggle() {
      this.searchBar = !this.searchBar;
    },
    selectSearchBarTravelKeyword(keyword) {
      keyword.selected = !keyword.selected;

      if (keyword.selected) {
        if (this.searchBarUserInputTravelKeywords.length > 0) {
          // 기존 선택된 거 취소
          this.searchBarTravelKeywords[
            this.selectedTravelKeywordIdx
          ].selected = false;
          this.deleteSearchVarTravelKeyword(this.selectedTravelKeywordIdx);

          // 새로운 거 선택
          this.selectedTravelKeywordIdx = keyword.idx;
          this.searchBarUserInputTravelKeywords.push(keyword.idx);
          this.searchTravels(keyword.id);
        } else {
          // 기존에 아무것도 없는 경우에는 해당 거 바로 선택
          this.searchBarUserInputTravelKeywords.push(keyword.idx);
          this.selectedTravelKeywordIdx = keyword.idx;
          this.searchTravels(keyword.id);
        }
      } else {
        this.deleteSearchVarTravelKeyword(keyword.idx);
      }
    },
    deleteSearchVarTravelKeyword(keywordIdx) {
      const idx = this.searchBarUserInputTravelKeywords.indexOf(keywordIdx);
      if (idx > -1) {
        this.searchBarUserInputTravelKeywords.splice(idx, 1);
      }
    },
    /* 카카오맵 지도 초기화 */
    initMap() {
      const container = document.getElementById('map');
      const DEFAULT_LAT = 37.566535;
      const DEFAULT_LON = 126.9779692;
      const options = {
        center: new kakao.maps.LatLng(DEFAULT_LAT, DEFAULT_LON),
        level: 5,
      };

      let map = new kakao.maps.Map(container, options);
      this.map = map;

      if (navigator.geolocation) {
        // GeoLocation을 이용해서 접속 위치를 얻어옴
        navigator.geolocation.getCurrentPosition(
          function(position) {
            let lat = position.coords.latitude, // 위도
              lon = position.coords.longitude; // 경도
            let locPosition = new kakao.maps.LatLng(lat, lon);

            // 지도 중심좌표를 접속위치로 변경
            map.setCenter(locPosition);
          }.bind(this),
        );
      } else {
        let locPosition = new kakao.maps.LatLng(DEFAULT_LAT, DEFAULT_LON);

        // 지도 중심좌표를 기본 위치로 변경
        map.setCenter(locPosition);
      }

      let searchContent = document.createElement('div');
      searchContent.style =
        'background: #F5C2B4; color: #000; text-align: center; line-height: 26px; width: 180px; height: 26px; border-radius: 0px; cursor: pointer';
      searchContent.textContent = '👉 여행지 검색하기';
      searchContent.onclick = this.searchTravelsForClick;

      let searchInfowindow = new kakao.maps.InfoWindow({
        content: searchContent,
        removable: true,
      });

      kakao.maps.event.addListener(
        map,
        'click',
        function(mouseEvent) {
          // 클릭한 위도, 경도 정보를 가져옴
          let latlng = mouseEvent.latLng;
          this.longitude = latlng.La;
          this.latitude = latlng.Ma;

          // 마커 이미지 생성
          let markerImage = new kakao.maps.MarkerImage(
            '/search_icon.png', // 마커이미지의 주소
            new kakao.maps.Size(50, 65), // 마커이미지의 크기
            { offset: new kakao.maps.Point(27, 69) }, // 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정
          );

          let marker = new kakao.maps.Marker({
            map: map,
            position: latlng,
            image: markerImage,
          });

          if (this.infoWin != null) {
            this.infoWin.setMap(null);
          }

          // 기존 마커 삭제
          for (let i = 0; i < this.markers.length; i++) {
            this.markers[i].setMap(null);
          }

          this.markers.push(marker);

          // 인포윈도우를 마커 위에 표시
          searchInfowindow.open(map, marker);
          this.infoWin = searchInfowindow;

          // 지도 중심좌표를 접속위치로 변경
          map.setCenter(latlng);
        }.bind(this),
      );

      // 인포윈도우 생성
      let registerContent = document.createElement('div');
      registerContent.style =
        'background: #50627f; color: #fff; text-align: center; width: 190px; height: 24px; line-height: 22px; border-radius: 4px; padding: 0px 10px; cursor: pointer';
      registerContent.textContent = '🏁 여행지 등록하기';
      registerContent.onclick = this.moveRegisterForm;

      let registerInfowindow = new kakao.maps.InfoWindow({
        content: registerContent,
        removable: true,
      });

      kakao.maps.event.addListener(
        map,
        'rightclick',
        function(mouseEvent) {
          // 클릭한 위도, 경도 정보를 가져옴
          let latlng = mouseEvent.latLng;
          this.longitude = latlng.La;
          this.latitude = latlng.Ma;

          // 마커 이미지 생성
          let markerImage = new kakao.maps.MarkerImage(
            '/logo.png', // 마커이미지의 주소
            new kakao.maps.Size(50, 65), // 마커이미지의 크기
            { offset: new kakao.maps.Point(27, 69) }, // 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정
          );

          let marker = new kakao.maps.Marker({
            map: map,
            position: latlng,
            image: markerImage,
          });

          if (this.infoWin != null) {
            this.infoWin.setMap(null);
          }

          // 기존 마커 삭제
          for (let i = 0; i < this.markers.length; i++) {
            this.markers[i].setMap(null);
          }

          this.markers.push(marker);

          // 인포윈도우를 마커 위에 표시
          registerInfowindow.open(map, marker);
          this.infoWin = registerInfowindow;

          // 지도 중심좌표를 접속위치로 변경
          map.setCenter(latlng);
        }.bind(this),
      );

      /* 현재 지도 중심 좌표 기준 여행지 검색 API 호출  */
      let center = this.map.getCenter();
      this.longitude = center.getLng();
      this.latitude = center.getLat();
      this.searchTravels();
    },
    async searchTravelsForClick() {
      // 기존 검색창 선택 취소
      for (let i = 0; i < this.searchBarTravelKeywords.length; i++) {
        this.searchBarTravelKeywords[i].selected = false;
      }

      this.searchBarUserInputTravelKeywords = [];

      this.searchTravels();
    },
    /* 클릭한 부분 여행지 검색 API 호출 */
    async searchTravels(keywordId) {
      // 기존 인포윈도우 삭제
      if (this.infoWin != null) {
        this.infoWin.setMap(null);
        this.infoWin = null;
      }

      // 기존 마커 삭제
      for (let i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }

      // 기존 여행지 데이터 삭제
      this.travelInfo = [];

      let response;

      if (Number.isInteger(keywordId)) {
        let center = this.map.getCenter();
        this.longitude = center.getLng();
        this.latitude = center.getLat();
        response = await getTravelInfoAroundCoordinateByKeywordId(
          keywordId,
          this.longitude,
          this.latitude,
        );
      } else {
        response = await getTravelInfoAroundCoordinate(
          this.longitude,
          this.latitude,
        );
      }

      const { data } = response;
      console.log('travel info data response...');

      // 받은 여행지 정보 좌표 기반 지도에 뿌려주기
      data.forEach(info => {
        this.travelInfo.push(info);

        // 클릭한 위도, 경도 정보를 가져옴
        let longitude = info.longitude;
        let latitude = info.latitude;

        let markerImageLogo = '/logo.png';
        if (info.travelKeyword === '운동시설') {
          markerImageLogo = '/travel/exercise.png';
        } else if (info.travelKeyword === '산책') {
          markerImageLogo = '/travel/walking.png';
        } else if (info.travelKeyword === '일몰') {
          markerImageLogo = '/travel/sunout.png';
        } else if (info.travelKeyword === '일출') {
          markerImageLogo = '/travel/sunin.png';
        } else if (info.travelKeyword === '감성') {
          markerImageLogo = '/travel/sense.png';
        } else if (info.travelKeyword === '야경') {
          markerImageLogo = '/travel/nightview.png';
        }

        // 마커 이미지 생성
        let markerImage = new kakao.maps.MarkerImage(
          markerImageLogo, // 마커이미지의 주소
          new kakao.maps.Size(35, 35), // 마커이미지의 크기
          { offset: new kakao.maps.Point(27, 69) }, // 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정
        );

        let marker = new kakao.maps.Marker({
          map: this.map,
          position: new kakao.maps.LatLng(latitude, longitude),
          image: markerImage,
        });

        kakao.maps.event.addListener(
          marker,
          'click',
          function() {
            this.openTravelInfoModal(`${info.travelId}`);
          }.bind(this),
        );

        this.markers.push(marker);
      });
    },
    async openTravelInfoModal(travelId) {
      try {
        const { data } = await getTravelInfoById(travelId);
        this.detailTravelInfo = data;
        this.$refs['detailTravelModal'].show();
      } catch (error) {
        alert('여행지 정보를 가져오는 과정에서 에러가 발생했습니다!');
      }
    },
    /* 여행지 등록 모달창 열기 */
    async moveRegisterForm() {
      // 기존 인포윈도우 삭제
      this.infoWin.setMap(null);
      this.infoWin = null;

      // 기존 마커 삭제
      for (let i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }

      if (!this.$store.getters.isLoggedIn) {
        alert('로그인이 필요한 작업입니다.');
        return;
      }
      this.$refs['registerTravelModal'].show();
    },
    /* 여행지 등록 모달창 닫기 */
    close() {
      this.userInputTravelName = '';
      this.userInputTravelDescription = '';
      for (let travelKeyword of this.travelKeywords) {
        if (travelKeyword.selected) {
          travelKeyword.selected = false;
        }
      }
      this.userInputTravelKeywords = [];
      this.userInputTravelPictures = [];
      this.longitude = 0;
      this.latitude = 0;
      this.$refs['registerTravelModal'].hide();
    },
    closeDetailTravelInfo() {
      this.detailTravelInfo = {};
      this.$refs['detailTravelModal'].hide();
    },
    /* 여행지 키워드 선택하기(토글) */
    selectTravelKeyword(keyword) {
      keyword.selected = !keyword.selected;
      if (keyword.selected) {
        this.userInputTravelKeywords.push(keyword.id);
      } else {
        this.deleteTravelKeyword(keyword);
      }
    },
    /* 선택한 여행지 키워드 취소 */
    deleteTravelKeyword(keyword) {
      const idx = this.userInputTravelKeywords.indexOf(keyword.id);
      if (idx > -1) {
        this.userInputTravelKeywords.splice(idx, 1);
      }
    },
    /* 여행지 등록 메서드 */
    async submit() {
      const body = {
        name: this.userInputTravelName,
        description: this.userInputTravelDescription,
        travelKeywordId: this.userInputTravelKeywords[0],
        files: this.userInputTravelPictures,
        longitude: this.longitude,
        latitude: this.latitude,
      };
      try {
        await registerTravel(body);
        alert('여행지 등록이 정상적으로 처리되었습니다. 🎉');
        this.$refs['registerTravelModal'].hide();
      } catch (error) {
        alert('여행지를 등록하는 과정에서 에러가 발생했습니다. 😢');
      }

      this.userInputTravelName = '';
      this.userInputTravelDescription = '';
      for (let travelKeyword of this.travelKeywords) {
        if (travelKeyword.selected) {
          travelKeyword.selected = false;
        }
      }
      this.userInputTravelKeywords = [];
      this.userInputTravelPictures = [];
      this.longitude = 0;
      this.latitude = 0;
    },
  },
  /* 컴포넌트 생성 시 여행지 키워드 삽입 */
  async created() {
    const { data } = await getTravelKeywords();
    for (let i = 0; i < data.length; i++) {
      const travelKeyword = {
        idx: i,
        id: data[i].id,
        name: data[i].name,
        selected: false,
      };
      this.searchBarTravelKeywords.push(travelKeyword);
      this.travelKeywords.push(travelKeyword);
    }
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 1000px;
}
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

.custom-button {
  background-color: #28a745;
  /* Update with desired background color */
  color: #ffffff;
  /* Update with desired text color */
}

.custom-button.disabled {
  background-color: #dc3545;
  /* Update with desired background color */
  color: #ffffff;
  /* Update with desired text color */
}

.form-input {
  display: inline;
  width: 95%;
  /* Set the desired width */
  margin-right: 10px;
}
/* 여행지 상세 정보 모달창 */
.title-box {
  background-color: #f8f9fa;
  padding: 10px 15px;
  border-radius: 4px;
  display: inline-block;
}

.title-text {
  font-weight: bold;
  font-size: 24px;
  margin: 0;
}

.modal-content {
  background-color: #f8f9fa;
  border-radius: 10px;
  padding: 20px;
}

.modal-content h2 {
  font-size: 24px;
}

.modal-content h3 {
  color: #333;
  font-size: 18px;
  margin-top: 20px;
}

.modal-content p {
  color: #555;
}

.custom-heading {
  color: #333; /* Set the text color */
  font-size: 24px; /* Adjust the font size */
  font-weight: bold; /* Set the font weight */
}

.image-list {
  display: flex;
  flex-wrap: wrap;
  margin-top: 10px;
}

.image-item {
  width: 50%;
  padding: 5px;
}

.image-preview {
  width: 100%;
  height: auto;
  border-radius: 5px;
}

.modal-content .button-container {
  display: flex;
  justify-content: flex-end;
  margin-top: 30px;
}
.travel-list .travel-container {
  display: flex;
  width: 90%;
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
  margin-bottom: 15px;
  margin: auto;
}

.travel-list .content {
  align-items: center;
}
.travel-list .image {
  padding: 8px 15px;
  color: #fff;
  border-radius: 4px;
  transition: background-color 0.3s;
  margin: auto;
}
</style>
