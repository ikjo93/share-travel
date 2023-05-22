<template>
  <div>
    <TravelSearchBar></TravelSearchBar>
    <div id="map"></div>
    <b-modal ref="my-modal" hide-footer hide-header>
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
        <div class="button-container">
          <b-button
            :disabled="!(travelNameState && travelKeywordState)"
            size="lg"
            :class="{
              'custom-button': travelNameState && travelKeywordState,
              disabled: !(travelNameState && travelKeywordState),
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
import { getTravelKeywords, registerTravel } from '@/api/travel.js';
import TravelSearchBar from '@/components/travel/TravelSearchBar.vue';

export default {
  name: 'KakaoMap',
  components: {
    TravelSearchBar,
  },
  data() {
    return {
      markers: [],
      infoWin: null,
      travelKeywords: [],
      userInputTravelName: '',
      userInputTravelDescription: '',
      userInputTravelKeywords: [],
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
    initMap() {
      const container = document.getElementById('map');
      const DEFAULT_LAT = 37.566535;
      const DEFAULT_LON = 126.9779692;
      const options = {
        center: new kakao.maps.LatLng(DEFAULT_LAT, DEFAULT_LON),
        level: 5,
      };

      let map = new kakao.maps.Map(container, options);

      // 마커 이미지 생성
      let imageSrc = '/logo.png', // 마커이미지의 주소
        imageSize = new kakao.maps.Size(50, 65), // 마커이미지의 크기
        imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정

      let markerImage = new kakao.maps.MarkerImage(
        imageSrc,
        imageSize,
        imageOption,
      );

      // 인포윈도우 생성
      let iwContent = document.createElement('div');
      iwContent.style =
        'background: #50627f; color: #fff; text-align: center; width: 190px; height: 24px; line-height: 22px; border-radius: 4px; padding: 0px 10px; cursor: pointer';
      iwContent.textContent = '여행지 등록하기 🏁';
      iwContent.onclick = this.moveRegisterForm;

      let infowindow = new kakao.maps.InfoWindow({
        content: iwContent,
        removable: true,
      });

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

      kakao.maps.event.addListener(
        map,
        'rightclick',
        function(mouseEvent) {
          // 클릭한 위도, 경도 정보를 가져옴
          let latlng = mouseEvent.latLng;

          let marker = new kakao.maps.Marker({
            map: map,
            position: latlng,
            image: markerImage,
          });

          // 기존 마커 삭제
          for (let i = 0; i < this.markers.length; i++) {
            this.markers[i].setMap(null);
          }

          this.markers.push(marker);

          // 인포윈도우를 마커 위에 표시
          infowindow.open(map, marker);
          this.infoWin = infowindow;

          // 지도 중심좌표를 접속위치로 변경
          map.setCenter(latlng);
        }.bind(this),
      );
    },
    moveRegisterForm() {
      this.$refs['my-modal'].show();

      // 기존 인포윈도우 삭제
      this.infoWin.setMap(null);
      this.infoWin = null;

      // 기존 마커 삭제
      for (let i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
    },
    close() {
      this.$refs['my-modal'].hide();
    },
    selectTravelKeyword(keyword) {
      keyword.selected = !keyword.selected;
      if (keyword.selected) {
        this.userInputTravelKeywords.push(keyword.id);
      } else {
        this.deleteTravelKeyword(keyword);
      }
    },
    deleteTravelKeyword(keyword) {
      const idx = this.userInputTravelKeywords.indexOf(keyword.id);
      if (idx > -1) {
        this.userInputTravelKeywords.splice(idx, 1);
      }
    },
    async submit() {
      const body = {
        travelName: this.userInputTravelName,
        travelDescription: this.userInputTravelDescription,
        travelKeywords: this.userInputTravelKeywords[0],
      };
      try {
        await registerTravel(body);
        alert('여행지 등록이 정상적으로 처리되었습니다. 🎉');
        this.$refs['my-modal'].hide();
      } catch (error) {
        alert('여행지를 등록하는 과정에서 에러가 발생했습니다. 😢');
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
