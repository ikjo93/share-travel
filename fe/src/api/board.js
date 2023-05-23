import { boards, boardsWithAuth } from '@/api/index';

// function getListByCondition() {}

function getListByCategory(categoryId) {
  return boards.get(`/${categoryId}`); //파라미터 주소 확인해서 수정
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
