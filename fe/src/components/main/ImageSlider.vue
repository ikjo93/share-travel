<template>
  <div>
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="4000"
      controls
      indicators
      background="#ababab"
      img-width="1024"
      img-height="460"
      style="text-shadow: 1px 1px 2px #333;"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <b-carousel-slide
        caption="당신만 아는 여행지가 있나요?"
        img-src="https://picsum.photos/1024/460/?image=33"
      ></b-carousel-slide>

      <b-carousel-slide
        caption="당신만 알고있는 운동 시설부터 멋진 뷰를 가진 장소가 있나요?"
        img-src="https://picsum.photos/1024/460/?image=155"
      ></b-carousel-slide>

      <b-carousel-slide
        caption="남들만 알고있는 여행지가 궁금한가요?"
        img-src="https://picsum.photos/1024/460/?image=1"
      ></b-carousel-slide>

      <b-carousel-slide
        caption="Share Travel 에서 당신만 아는 여행지를 공유하고 다른 사람들만 아는 여행지 정보를 얻어가세요!"
        img-src="https://picsum.photos/1024/460/?image=2"
      ></b-carousel-slide>
    </b-carousel>
  </div>
</template>

<script>
export default {
  data() {
    return {
      slide: 0,
      sliding: null,
    };
  },
  methods: {
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    },
  },
};
</script>

<style></style>
