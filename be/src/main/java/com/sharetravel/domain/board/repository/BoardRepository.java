package com.sharetravel.domain.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sharetravel.domain.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board,Long> {
	// Object findByCondition(String title, String nickName, String keyword);
	// Object findAllByBoardType(String boardType);

    @Query("select b from Board b where b.category.id = :categoryId")
	List<Board> findAllByCategoryId(@Param("categoryId") Long categoryId);
}
