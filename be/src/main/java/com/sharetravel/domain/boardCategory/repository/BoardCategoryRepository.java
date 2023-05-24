package com.sharetravel.domain.boardcategory.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sharetravel.domain.boardcategory.entity.BoardCategory;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory,Long> {

    @EntityGraph(value = "BoardCategoryWithAll")
    Optional<BoardCategory> findById(Long categoryId);
}
