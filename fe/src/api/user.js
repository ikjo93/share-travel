import { users } from '@/api/index';

function getUserInfo() {
  return users.get();
}

function updateUser(data) {
  return users.put(`/${data.id}`, data);
}

export { getUserInfo, updateUser };
