<template>
  <div>
    <TravelSearchBar></TravelSearchBar>
    <div id="map"></div>
  </div>
</template>

<script>
import TravelSearchBar from '@/components/travel/TravelSearchBar.vue';

export default {
  name: 'KakaoMap',
  components: {
    TravelSearchBar,
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
      let message = '<div style="padding:5px;">현재 위치</div>';
      let iwContent = message, // 인포윈도우에 표시할 내용
        iwRemoveable = true;

      // 인포윈도우를 생성
      let infowindow = new kakao.maps.InfoWindow({
        content: iwContent,
        removable: iwRemoveable,
      });

      if (navigator.geolocation) {
        // GeoLocation을 이용해서 접속 위치를 얻어옴
        navigator.geolocation.getCurrentPosition(function(position) {
          let lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도
          let locPosition = new kakao.maps.LatLng(lat, lon); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다

          // 마커를 생성
          let marker = new kakao.maps.Marker({
            map: map,
            position: locPosition,
            image: markerImage,
          });

          // 인포윈도우를 마커위에 표시
          infowindow.open(map, marker);

          // 지도 중심좌표를 접속위치로 변경
          map.setCenter(locPosition);
        });
      } else {
        let locPosition = new kakao.maps.LatLng(DEFAULT_LAT, DEFAULT_LON);

        // 지도 중심좌표를 기본 위치로 변경
        map.setCenter(locPosition);
      }
    },
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 1000px;
}
</style>
