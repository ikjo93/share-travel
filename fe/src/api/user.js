import { users } from './index';

function updateUser(data) {
  return users.put(`${data.id}`, data);
}

export { updateUser };
