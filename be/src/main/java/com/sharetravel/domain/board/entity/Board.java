package com.sharetravel.domain.board.entity;

import com.sharetravel.domain.boardCategory.entity.BoardCategory;
import com.sharetravel.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "`board`")
@Entity
public class Board extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long id;

	@Column(length = 30, name = "nickname")
	@JoinColumn(name = "nickname", nullable = false)
	private String nickName;

	@Column(length = 50, nullable = false)
	private String title;

	@Column(length = 40)
	private String subTitle;

	@Column(length = 1500, nullable = false)
	private String content;

	private Integer hit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_category_id")
	private BoardCategory category;

	@Builder
	public Board(String nickName, String title,
		String subTitle, String content, Integer hit,
		BoardCategory category) {
		this.nickName = nickName;
		this.title = title;
		this.subTitle = subTitle;
		this.content = content;
		this.hit = hit;
		this.category = category;
	}

	public Board update(String title, String subTitle, String content) {
		this.title = title;
		this.subTitle = subTitle;
		this.content = content;

		return this;
	}
}
