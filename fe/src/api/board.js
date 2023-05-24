import { boards, boardsWithAuth } from '@/api/index';

// function getListByCondition() {}

function getListByCategory(categoryId) {
  return boards.get(`?categoryId=${categoryId}`);
}

function getListByCondition(categoryId, searchType, keyword) {
  return boards.get(
    `?categoryId=${categoryId}&searchType=${searchType}&keyword=${keyword}`,
  );
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
  return boardsWithAuth.delete(`/${boardId}`);
}

export {
  getListByCategory,
  getListByCondition,
  getDetail,
  registBoard,
  updateBoard,
  deleteBoard,
};
