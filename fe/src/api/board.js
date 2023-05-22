import { boards } from '@/api/index';
import { board } from '@/api/index';

// function getListByCondition() {}

function getListByCategory(categoryId) {
  return boards.get(`/${categoryId}`); //파라미터 주소 확인해서 수정
}

function getDetail(boardId) {
  return board.get(`/${boardId}`);
}

function registBoard(data) {
  return board.post(``, data);
}

function updatBoard(boardId, data) {
  return board.put(`/${boardId}`, data);
}

function deleteBoard(boardId) {
  return board.delete(`/${boardId}`);
}

export { getListByCategory, getDetail, registBoard, updatBoard, deleteBoard };
