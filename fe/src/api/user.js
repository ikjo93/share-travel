import { users } from './index';

function getUserInfo() {
  return users.get();
}

function updateUser(data) {
  return users.put(`${data.id}`, data);
}

export { getUserInfo, updateUser };
