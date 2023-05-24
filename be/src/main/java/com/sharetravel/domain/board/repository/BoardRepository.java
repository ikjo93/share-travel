package com.sharetravel.domain.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sharetravel.domain.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board,Long> {

    @Query("select b from Board b where b.category.id = :categoryId and b.title LIKE %:keyword%")
    @EntityGraph(attributePaths = {"author"})
	List<Board> findAllByTitleKeyword(@Param("categoryId") Long categoryId, @Param("keyword") String keyword);

    @Query("select b from Board b where b.category.id = :categoryId and b.author.nickName LIKE %:keyword%")
    @EntityGraph(attributePaths = {"author"})
    List<Board> findAllByNicknameKeyword(@Param("categoryId") Long categoryId, @Param("keyword") String keyword);

    @Query("select b from Board b where b.id = :id")
    @EntityGraph(attributePaths = {"author", "category"})
    Optional<Board> findWithAllById(@Param("id") Long boardId);
}
