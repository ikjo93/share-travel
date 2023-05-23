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

// user.js 에서 회원 탈퇴 처리 시 사용
export const commonApiWithAuth = createWithAuth(
  `${process.env.VUE_APP_API_URL}api`,
  { withCredentials: true },
);

export const auth = create(`${process.env.VUE_APP_API_URL}api/tokens`, {
  withCredentials: true,
});

export const users = createWithAuth(`${process.env.VUE_APP_API_URL}api/users`);

export const travelKeywords = create(
  `${process.env.VUE_APP_API_URL}api/travelkeywords`,
);

export const travels = createWithAuth(
  `${process.env.VUE_APP_API_URL}api/travels`,
);

export const boards = create(`${process.env.VUE_APP_API_URL}api/boards`);
