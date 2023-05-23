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

/* 쿼리 스트링을 포함한 API 요청 인스턴스 집합 */

export const commonApi = create(`${process.env.VUE_APP_API_URL}api`);

export const commonApiWithAuth = createWithAuth(
  `${process.env.VUE_APP_API_URL}api`,
  { withCredentials: true },
);

/*  도메인별 요청 인스턴스 집합   */

export const auth = create(`${process.env.VUE_APP_API_URL}api/tokens`, {
  withCredentials: true,
});

export const users = createWithAuth(`${process.env.VUE_APP_API_URL}api/users`);

export const travelKeywords = create(
  `${process.env.VUE_APP_API_URL}api/travelkeywords`,
);

export const travels = create(`${process.env.VUE_APP_API_URL}api/travels`);

export const travelsWithAuth = createWithAuth(
  `${process.env.VUE_APP_API_URL}api/travels`,
);

export const boards = create(`${process.env.VUE_APP_API_URL}api/boards`);
