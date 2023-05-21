import { auth } from '@/api/index';

function reissueAccessToken() {
  return auth.post();
}

function logout() {
  return auth.delete();
}

export { reissueAccessToken, logout };
