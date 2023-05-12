import store from '@/store/index.js';

function setInterceptors(instance) {
  instance.interceptors.request.use(
    config => {
      config.headers.Authorization = `Bearer ${store.getters['userToken']}`;
      return config;
    },
    error => Promise.reject(error.response),
  );
  instance.interceptors.response.use(
    config => config,
    error => Promise.reject(error.response),
  );
  return instance;
}

export { setInterceptors };
