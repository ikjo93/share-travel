package com.sharetravel.domain.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sharetravel.domain.board.dto.BoardDto;
import com.sharetravel.domain.board.entity.Board;
import com.sharetravel.domain.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;

	@Transactional(readOnly = true)
	public List<BoardDto> findByCondition(String title, String nickName, String keyword, String boardType) {
		// List<BoardDto> boardList = boardRepository.findByCondition(title,nickName,keyword)
		// 	.orElseThrow(() -> {
		//
		// 	});
		return null;
	}

	@Transactional(readOnly = true)
	public List<BoardDto> findAllByCategory(Long categoryId) {
		// return boardRepository.findAllByBoardType(boardType);
		boardRepository.findAllByCategoryId(categoryId);
		return null;
	}

	@Transactional(readOnly = true)
	public BoardDto findById(Long boardId) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> {
				throw new IllegalStateException(boardId + "번 게시글이 존재하지 않습니다.");
			});
		return BoardDto.from(board);
	}

	@Transactional
	public Board save(BoardDto boardDto) {
		return boardRepository.save(boardDto.toEntity());
	}

	@Transactional
	public Board update(Long boardId, String title, String subTitle, String content) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> {
				throw new IllegalStateException(boardId + "번 게시글이 존재하지 않습니다.");
			});
		board.update(title,subTitle,content);
		return board;
	}

	@Transactional
	public void delete(Long boardId) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> {
				throw new IllegalStateException(boardId + "번 게시글이 존재하지 않습니다.");
			});
		boardRepository.delete(board);
	}
}
