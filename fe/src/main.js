import Vue from 'vue';
import App from './App.vue';
import router from './routes';
import store from './store';
import { BootstrapVue, IconsPlugin, CarouselPlugin } from 'bootstrap-vue';

import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

Vue.config.productionTip = false;

Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
Vue.use(CarouselPlugin);

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
