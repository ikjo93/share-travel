package com.sharetravel.domain.boardCategory.dto;

import com.sharetravel.domain.boardCategory.entity.BoardCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardCategoryDto {

	private Long categoryId;
	private String categoryName;

	public static BoardCategoryDto from(BoardCategory boardCategory) {
		return new BoardCategoryDto();
	}

	public BoardCategory toEntity() {
		return BoardCategory.builder()
			.categoryName(categoryName)
			.build();
	}
}
