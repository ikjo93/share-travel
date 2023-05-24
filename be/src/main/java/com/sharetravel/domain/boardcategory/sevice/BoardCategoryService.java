package com.sharetravel.domain.boardcategory.sevice;

import com.sharetravel.domain.board.dto.BoardResponseDto;
import java.util.List;

import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sharetravel.domain.boardcategory.entity.BoardCategory;
import com.sharetravel.domain.boardcategory.repository.BoardCategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardCategoryService {

    private final BoardCategoryRepository boardCategoryRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> findAllByCategoryId(Long categoryId) {
        BoardCategory boardCategory = boardCategoryRepository.findById(categoryId)
            .orElseThrow(() -> {
                throw new IllegalStateException("식별번호가 " + categoryId + "에 해당하는 카테고리가 존재하지 않습니다.");
            });

        return boardCategory.getBoards()
                .stream()
                .map(board -> BoardResponseDto.from(categoryId, board))
                .collect(Collectors.toList());
    }
}
