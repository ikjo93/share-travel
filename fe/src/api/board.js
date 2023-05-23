import { boards, boardsWithAuth } from '@/api/index';

// function getListByCondition() {}

function getListByCategory(categoryId) {
  return boards.get(`/category/${categoryId}`);
}

function getDetail(boardId) {
  return boards.get(`/${boardId}`);
}

function registBoard(board) {
  return boardsWithAuth.post(``, board);
}

function updatBoard(boardId, data) {
  return boards.put(`/${boardId}`, data);
}

function deleteBoard(boardId) {
  return boards.delete(`/${boardId}`);
}

export { getListByCategory, getDetail, registBoard, updatBoard, deleteBoard };
