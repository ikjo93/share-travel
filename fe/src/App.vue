<template>
  <div class="app">
    <AppHeader></AppHeader>
    <div class="app-contents">
      <router-view></router-view>
    </div>
    <AppFooter></AppFooter>
  </div>
</template>

<script>
import AppHeader from './components/common/AppHeader.vue';
import AppFooter from './components/common/AppFooter.vue';

export default {
  components: {
    AppHeader,
    AppFooter,
  },
};
</script>

<style></style>
