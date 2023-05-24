package com.sharetravel.domain.board.entity;

import com.sharetravel.domain.board.dto.BoardUpdateRequestDto;
import com.sharetravel.domain.boardcategory.entity.BoardCategory;
import com.sharetravel.domain.user.entity.User;
import com.sharetravel.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedEntityGraph(name = "Board.withAuthor", attributeNodes = {
    @NamedAttributeNode("author")
})
@Table(name = "board")
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(name = "subtitle", length = 100, nullable = false)
    private String subTitle;

    @Column(length = 1500, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_category_id")
    private BoardCategory category;

    @Builder
    public Board(String title,
        String subTitle, String content,
        User user, BoardCategory category) {
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.author = user;
        this.category = category;
    }

    public Board update(BoardUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.subTitle = requestDto.getSubTitle();
        this.content = requestDto.getContent();

        return this;
    }

    public String getNickNameOfAuthor() {
        return author.getNickName();
    }
}
