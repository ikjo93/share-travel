package com.sharetravel.domain.boardCategory.entity;

import java.util.ArrayList;
import java.util.List;

import com.sharetravel.domain.board.entity.Board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "'board_category'")
@Entity
public class BoardCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_category_id")
	private Long id;

	@Column(name = "category_name")
	private String categoryName;

	@OneToMany(mappedBy = "category")
	private List<Board> boards = new ArrayList<>();

	@Builder
	public BoardCategory(String categoryName) {
		this.categoryName = categoryName;
	}
}
