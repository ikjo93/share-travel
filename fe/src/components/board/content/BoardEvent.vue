<template lang="">
  <div>
    <div v-if="false"></div>
    <div v-else class="no-content">
      <img src="../../../../public/icon_noresult.png" />
      <p>아직 이벤트가 없어요 !</p>
    </div>
  </div>
</template>
<script>
export default {};
</script>
<style scoped>
.no-content {
  width: fit-content;
  margin: auto;
  padding-top: 60px;
}
</style>
