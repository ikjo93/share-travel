package com.sharetravel.domain.boardCategory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.sharetravel.domain.boardCategory.dto.BoardCategoryDto;
import com.sharetravel.domain.boardCategory.entity.BoardCategory;
import com.sharetravel.domain.boardCategory.sevice.BoardCategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BoardCategoryController {

	private final BoardCategoryService boardCategoryService;

	// 모든 게시판 종류 가져오기
	public List<BoardCategory> findAll() {
		return boardCategoryService.findAll();
	}

	// 게시판 타입 가져오기
	public BoardCategoryDto findById(Long categoryId) {
		return boardCategoryService.findById(categoryId);
	}

}
