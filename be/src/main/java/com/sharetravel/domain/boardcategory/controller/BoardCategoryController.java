package com.sharetravel.domain.boardcategory.controller;

import static com.sharetravel.global.api.ApiUtil.getResponseEntity;

import com.sharetravel.domain.board.dto.BoardResponseDto;
import com.sharetravel.global.api.ApiResponseCode;
import com.sharetravel.global.api.ApiResponseMessage;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sharetravel.domain.boardcategory.sevice.BoardCategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BoardCategoryController {

    private final BoardCategoryService boardCategoryService;

    @GetMapping("/api/boards")
    public List<BoardResponseDto> findAllByCategoryId(@RequestParam Long categoryId) {
        return boardCategoryService.findAllByCategoryId(categoryId);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponseMessage> handleUserNotFoundException(IllegalStateException e) {
        e.printStackTrace();
        return getResponseEntity(ApiResponseCode.BOARD_CATEGORY_NOT_FOUND);
    }
}
