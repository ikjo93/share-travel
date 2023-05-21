import store from '@/store/index.js';
import { reissueAccessToken } from '@/api/auth';

function setInterceptors(instance) {
  instance.interceptors.request.use(
    config => {
      if (store.getters['isLoggedIn']) {
        config.headers.Authorization = `Bearer ${store.getters['userToken']}`;
        return config;
      } else {
        return Promise.reject(new Error('로그인이 필요한 작업입니다.'));
      }
    },
    error => Promise.reject(error.response),
  );
  instance.interceptors.response.use(
    async response => {
      // 액세스 토큰이 만료된 경우
      if (response.data.code === 'A03') {
        const { data } = await reissueAccessToken();
        // 액세스 토큰이 재발급된 경우
        if (data.code === 'A05') {
          store.commit('LOGIN', data.accessToken);
          return instance(response.config);
        } else {
          return Promise.reject(
            new Error(
              '로그인 시간 제한이 만료되었습니다. 다시 로그인해주세요.',
            ),
          );
        }
      }

      return response;
    },
    error => Promise.reject(error.response),
  );
  return instance;
}

export { setInterceptors };
