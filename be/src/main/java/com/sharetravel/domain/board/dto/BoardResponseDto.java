package com.sharetravel.domain.board.dto;

import com.sharetravel.domain.board.entity.Board;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardResponseDto {

    private Long categoryId;
    private Long boardId;
    private String nickName;
    private String title;
    private String subTitle;
    private String content;

    public static BoardResponseDto from(Long categoryId, Board board) {
        return new BoardResponseDto(categoryId, board.getId(), board.getNickNameOfAuthor(), board.getTitle(),
            board.getSubtitle(), board.getContent());
    }
}
