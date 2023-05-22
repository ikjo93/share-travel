import { users, commonApiWithAuth } from '@/api/index';

function getUserInfo() {
  return users.get();
}

function validateUserNickName(nickName) {
  return users.get(`duplicate?nickName=${nickName}`);
}

function registerUser(data) {
  return users.post('', data);
}

function getAuthCode() {
  return users.post('mail');
}

function confirmAuthCode(code) {
  return commonApiWithAuth.delete(`users?code=${code}`);
}

export {
  getUserInfo,
  validateUserNickName,
  registerUser,
  getAuthCode,
  confirmAuthCode,
};
