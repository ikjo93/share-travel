package com.sharetravel.domain.board.dto;

import com.sharetravel.domain.board.entity.Board;
import com.sharetravel.domain.boardCategory.entity.BoardCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

	private Long boardId;
	private String nickName;
	private String title;
	private String subTitle;
	private String content;
	private BoardCategory categoryId;
	private int hit;

	public static BoardDto from(Board board) {
		return new BoardDto(board.getId(),board.getNickName(),board.getTitle(), board.getSubTitle(),
			board.getContent(),board.getCategory(),board.getHit());
	}

	public Board toEntity() {
		return Board.builder()
			.nickName(nickName)
			.title(title)
			.subTitle(subTitle)
			.content(content)
			.category(categoryId)
			.hit(hit)
			.build();
	}
}
