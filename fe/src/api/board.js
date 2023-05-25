import { commonApi, boards, boardsWithAuth } from '@/api/index';

function getListByCategory(categoryId) {
  return commonApi.get(`boards?categoryId=${categoryId}`);
}

function getListByCondition(categoryId, searchType, keyword) {
  return commonApi.get(
    `boards/search?categoryId=${categoryId}&searchType=${searchType}&keyword=${keyword}`,
  );
}

function getDetail(boardId) {
  return boards.get(`/${boardId}`);
}

function registBoard(board) {
  return boardsWithAuth.post(``, board);
}

function updateBoard(boardId, updateData) {
  return boardsWithAuth.put(`/${boardId}`, updateData);
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
