import { users } from '@/api/index';

function getUserInfo() {
  return users.get();
}

function validateUserNickName(nickName) {
  return users.get(`/duplicate?nickName=${nickName}`);
}

function registerUser(data) {
  return users.post('', data);
}

export { getUserInfo, validateUserNickName, registerUser };
