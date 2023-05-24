package com.sharetravel.domain.board.controller;

import static com.sharetravel.global.api.ApiUtil.getResponseEntity;

import com.sharetravel.domain.board.dto.BoardSaveRequestDto;
import com.sharetravel.domain.board.dto.BoardSearchCondition;
import com.sharetravel.domain.board.dto.BoardUpdateRequestDto;
import jakarta.validation.Valid;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sharetravel.domain.board.dto.BoardResponseDto;
import com.sharetravel.domain.board.entity.Board;
import com.sharetravel.domain.board.service.BoardService;
import com.sharetravel.global.api.ApiResponseCode;
import com.sharetravel.global.api.ApiResponseMessage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/api/boards/search")
    public List<BoardResponseDto> searchByCondition(@Valid @ModelAttribute BoardSearchCondition condition) {
        return boardService.findAllByCondition(condition);
    }

    @GetMapping("/api/boards/{id}")
    public BoardResponseDto find(@PathVariable Long id) {
        return boardService.findById(id);
    }

    @PostMapping("/api/boards")
    public BoardResponseDto save(@AuthenticationPrincipal Long userId, @Valid @RequestBody BoardSaveRequestDto requestDto) {
        return boardService.save(userId, requestDto);
    }

    @PutMapping("/api/boards/{id}")
    public BoardResponseDto update(@PathVariable Long id, @Valid @RequestBody BoardUpdateRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    // 게시글 삭제
    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<ApiResponseMessage> delete(@PathVariable Long id) {
        boardService.delete(id);
        return getResponseEntity(ApiResponseCode.BOARD_DELETE_SUCCESS);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponseMessage> handleInvalidTokenException() {
        return getResponseEntity(ApiResponseCode.REQUEST_RESOURCE_NOT_FOUND);
    }
}
