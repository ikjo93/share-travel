<template>
  <div>
    <TravelSearchBar></TravelSearchBar>
    <div id="map"></div>
    <b-modal
      ref="my-modal"
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
  </div>
</template>

<script>
import {
  getTravelKeywords,
  getTravelInfo,
  registerTravel,
} from '@/api/travel.js';
import TravelSearchBar from '@/components/travel/TravelSearchBar.vue';

export default {
  name: 'KakaoMap',
  components: {
    TravelSearchBar,
  },
  data() {
    return {
      map: null,
      markers: [],
      infoWin: null,
      travelKeywords: [],
      userInputTravelName: '',
      userInputTravelDescription: '',
      userInputTravelKeywords: [],
      userInputTravelPictures: [],
      longitude: 0,
      latitude: 0,
      travelInfo: [],
    };
  },
  computed: {
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

      // 마커 이미지 생성
      let markerImage = new kakao.maps.MarkerImage(
        '/logo.png', // 마커이미지의 주소
        new kakao.maps.Size(50, 65), // 마커이미지의 크기
        { offset: new kakao.maps.Point(27, 69) }, // 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정
      );

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
        'background: #FFFF33; color: #000; text-align: center; width: 190px; height: 24px; line-height: 22px; border-radius: 4px; padding: 0px 10px; cursor: pointer';
      searchContent.textContent = '👉 여행지 검색하기';
      searchContent.onclick = this.searchTravels;

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
    /* 클릭한 부분 여행지 검색 API 호출 */
    async searchTravels() {
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

      const { data } = await getTravelInfo(this.longitude, this.latitude);
      console.log('travel info data response...');

      // 받은 여행지 정보 좌표 기반 지도에 뿌려주기
      data.forEach(info => {
        this.travelInfo.push(info);
        console.log(info);

        // 클릭한 위도, 경도 정보를 가져옴
        let longitude = info.longitude;
        let latitude = info.latitude;
        console.log(longitude, latitude);

        // 마커 이미지 생성
        let markerImage = new kakao.maps.MarkerImage(
          '/logo.png', // 마커이미지의 주소
          new kakao.maps.Size(50, 65), // 마커이미지의 크기
          { offset: new kakao.maps.Point(27, 69) }, // 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정
        );

        let marker = new kakao.maps.Marker({
          map: this.map,
          position: new kakao.maps.LatLng(latitude, longitude),
          image: markerImage,
        });

        this.markers.push(marker);

        // kakao.maps.event.addListener(
        //   this.map,
        //   'click',
        //   function() {}.bind(this),
        // );
      });
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
      this.$refs['my-modal'].show();
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
      this.$refs['my-modal'].hide();
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
        this.$refs['my-modal'].hide();
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
</style>
