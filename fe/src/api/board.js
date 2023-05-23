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

function updateBoard(boardId, data) {
  return boardsWithAuth.put(`/${boardId}`, data);
}

function deleteBoard(boardId) {
  console.log('악시오스 안 ', boardId);
  return boardsWithAuth.delete(`/${boardId}`);
}

export { getListByCategory, getDetail, registBoard, updateBoard, deleteBoard };
