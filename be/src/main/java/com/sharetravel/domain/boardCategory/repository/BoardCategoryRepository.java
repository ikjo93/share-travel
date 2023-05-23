package com.sharetravel.domain.boardCategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharetravel.domain.boardCategory.entity.BoardCategory;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory,Long> {

}
