package com.sharetravel.domain.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sharetravel.domain.board.dto.BoardDto;
import com.sharetravel.domain.board.entity.Board;
import com.sharetravel.domain.board.service.BoardService;
import com.sharetravel.global.ApiResponseCode;
import com.sharetravel.global.ApiResponseMessage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BoardController {

	private final BoardService boardService;

	// 조건으로 게시글 목록 가져오기
	@GetMapping("/api/boards")
	public List<BoardDto> findByCondition(String title, String nickName, String keyword, String boardType) {
		return boardService.findByCondition(title,nickName,keyword,boardType);
	}

	// 카테고리 별 게시글 가져오기
	public List<BoardDto> findAllByCategory(Long categoryId) {
		return boardService.findAllByCategory(categoryId);
	}

	// 게시글(1개) 상세보기
	@GetMapping("/api/board/{boardId}")
	public BoardDto find(@PathVariable("boardId") Long boardId) {
		return boardService.findById(boardId);
	}

	// 새로운 게시글 작성
	@PostMapping("/api/board")
	public Board save(@RequestBody BoardDto boardDto) {
		return boardService.save(boardDto);
	}

	// 기존 게시글 수정
	@PutMapping("/api/board/{boardId}")
	public Board update(@PathVariable("boardId") Long boardId, String title, String subTitle, String content) {
		return boardService.update(boardId,title,subTitle,content);
	}

	// 게시글 삭제
	@DeleteMapping("/api/board/{boardId}")
	public ResponseEntity<ApiResponseMessage> delete(@PathVariable("boardId") Long boardId) {
		boardService.delete(boardId);
		ApiResponseCode apiResponseCode = ApiResponseCode.BOARD_DELETE_SUCCESS;
		ApiResponseMessage apiResponseMessage = ApiResponseMessage.of(apiResponseCode.getCode(),
			apiResponseCode.getMessage());
		return ResponseEntity.status(apiResponseCode.getHttpStatusCode()).body(apiResponseMessage);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ApiResponseMessage> handleInvalidTokenException() {
		ApiResponseCode apiResponseCode = ApiResponseCode.BOARD_NOT_FOUND;
		ApiResponseMessage apiResponseMessage = ApiResponseMessage.of(apiResponseCode.getCode(), apiResponseCode.getMessage());
		return ResponseEntity.status(apiResponseCode.getHttpStatusCode()).body(apiResponseMessage);
	}
}
