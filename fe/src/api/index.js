import axios from 'axios';
import { setInterceptors } from './config/interceptors';

function create(url, options) {
  const instance = axios.create(Object.assign({ baseURL: url }, options));
  return instance;
}

function createWithAuth(url, options) {
  const instance = axios.create(Object.assign({ baseURL: url }, options));
  setInterceptors(instance);
  return instance;
}

export const naverOauth2 = create(
  `${process.env.VUE_APP_API_URL}login/oauth2/code/naver`,
);
export const kakaoOauth2 = create(
  `${process.env.VUE_APP_API_URL}login/oauth2/code/kakao`,
);
export const googleOauth2 = create(
  `${process.env.VUE_APP_API_URL}login/oauth2/code/google`,
);
export const users = createWithAuth(`${process.env.VUE_APP_API_URL}users/`);
