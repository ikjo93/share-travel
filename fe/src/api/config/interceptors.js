import store from '@/store/index.js';
import { tokens } from '@/api/index';

function setInterceptors(instance) {
  instance.interceptors.request.use(
    config => {
      if (store.getters['isLoggedIn']) {
        config.headers.Authorization = `Bearer ${store.getters['userToken']}`;
        return config;
      } else {
        alert('로그인이 필요한 작업입니다.');
        return Promise.reject(new Error('Request skipped.'));
      }
    },
    error => Promise.reject(error.response),
  );
  instance.interceptors.response.use(
    config => {
      if (config.data.code === 'A03') {
        // 액세스 토큰이 만료된 경우
        const res = requsetRenewAccessToken();
        const code = res.data.code;
        if (code === 'A05') {
          // 액세스 토큰이 재발급된 경우
          this.$store.commit('LOGIN');
          return instance(config);
        } else {
          alert('로그인 시간 제한이 만료되었습니다. 다시 로그인해주세요.');
          return Promise.reject(new Error('Request skipped.'));
        }
      }

      return config;
    },
    error => Promise.reject(error.response),
  );
  return instance;
}

async function requsetRenewAccessToken() {
  const response = await tokens.post();
  return response;
}

export { setInterceptors };
