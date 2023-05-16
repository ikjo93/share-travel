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

export const tokens = create(`${process.env.VUE_APP_API_URL}api/token/reissue`);

export const users = createWithAuth(`${process.env.VUE_APP_API_URL}api/users`);
