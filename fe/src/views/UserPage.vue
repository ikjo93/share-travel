<template>
  <div>
    <b-container class="bv-example-row">
      <b-row>
        <b-col cols="4">
          <UserNavigation></UserNavigation>
        </b-col>
        <b-col cols="8">
          <div class="content-container">
            <router-view></router-view>
          </div>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import UserNavigation from '@/components/user/mypage/UserNavigation.vue';

export default {
  components: {
    UserNavigation,
  },
};
</script>

<style></style>
