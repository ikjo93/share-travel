import { users } from '@/api/index';

function getUserInfo() {
  return users.get();
}

function updateUser(data) {
  return users.put(`/${data.id}`, data);
}

function validateUserNickName(nickName) {
  return users.get(`/duplicate?nickName=${nickName}`);
}

export { getUserInfo, updateUser, validateUserNickName };
