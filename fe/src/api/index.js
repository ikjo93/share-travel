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

export const reissueAccessToken = create(
  `${process.env.VUE_APP_API_URL}api/token/reissue`,
  { withCredentials: true },
);

export const users = createWithAuth(`${process.env.VUE_APP_API_URL}api/users`);

export const travelKeywords = create(
  `${process.env.VUE_APP_API_URL}api/travelkeywords`,
);

export const boards = create(`${process.env.VUE_APP_API_URL}api/boards`);

export const board = create(`${process.env.VUE_APP_API_URL}api/board`);
