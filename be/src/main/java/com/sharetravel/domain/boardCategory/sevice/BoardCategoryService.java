package com.sharetravel.domain.boardCategory.sevice;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sharetravel.domain.boardCategory.dto.BoardCategoryDto;
import com.sharetravel.domain.boardCategory.entity.BoardCategory;
import com.sharetravel.domain.boardCategory.repository.BoardCategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardCategoryService {

	private final BoardCategoryRepository boardCategoryRepository;

	@Transactional
	public List<BoardCategory> findAll() {
		return boardCategoryRepository.findAll();
	}

	@Transactional
	public BoardCategoryDto findById(Long categoryId) {
		BoardCategory boardCategory = boardCategoryRepository.findById(categoryId)
			.orElseThrow(() -> {
				throw new IllegalStateException(categoryId + "번 카테고리가 존재하지 않습니다.");
			});
		return BoardCategoryDto.from(boardCategory);
	}

}
