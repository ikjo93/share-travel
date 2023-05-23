package com.sharetravel.domain.board.service;

import com.sharetravel.domain.board.dto.BoardSaveRequestDto;
import com.sharetravel.domain.board.dto.BoardSearchCondition;
import com.sharetravel.domain.board.dto.BoardUpdateRequestDto;
import com.sharetravel.domain.boardcategory.entity.BoardCategory;
import com.sharetravel.domain.boardcategory.repository.BoardCategoryRepository;
import com.sharetravel.domain.user.entity.User;
import com.sharetravel.domain.user.repository.UserRepository;
import java.util.List;

import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sharetravel.domain.board.dto.BoardResponseDto;
import com.sharetravel.domain.board.entity.Board;
import com.sharetravel.domain.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardCategoryRepository boardCategoryRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> findAllByCondition(BoardSearchCondition condition) {
        List<Board> boards;
        if (condition.isTitle()) {
            boards = boardRepository.findAllByTitleKeyword(condition.getCategoryId(), condition.getKeyword());
        } else {
            boards = boardRepository.findAllByNicknameKeyword(condition.getCategoryId(), condition.getKeyword());
        }
        return boards.stream()
            .map(board -> BoardResponseDto.from(condition.getCategoryId(), board))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BoardResponseDto findById(Long id) {
        Board board = boardRepository.findWithAllById(id).orElseThrow(() -> {
            throw new IllegalStateException("식별 번호가 " + id + "에 해당하는 게시글이 존재하지 않습니다.");
        });

        return BoardResponseDto.from(null, board);
    }

    @Transactional
    public BoardResponseDto save(Long userId, BoardSaveRequestDto requestDto) {
        User user = getUserById(userId);
        Long categoryId = requestDto.getCategoryId();
        BoardCategory boardCategory = getBoardCategoryById(categoryId);

        Board board = Board.builder()
            .category(boardCategory)
            .user(user)
            .title(requestDto.getTitle())
            .subTitle(requestDto.getSubTitle())
            .content(requestDto.getContent())
            .build();

        return BoardResponseDto.from(categoryId, boardRepository.save(board));
    }

    @Transactional
    public BoardResponseDto update(Long boardId, BoardUpdateRequestDto requestDto) {
        Board board = getBoardById(boardId);
        return BoardResponseDto.from(null, board.update(requestDto));
    }

    @Transactional
    public void delete(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    private Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId)
            .orElseThrow(() -> {
                throw new IllegalStateException(boardId + "번 게시글이 존재하지 않습니다.");
            });
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> {
                throw new IllegalStateException("식별 번호 " + userId + "에 해당하는 사용자가 존재하지 않습니다.");
            });
    }

    private BoardCategory getBoardCategoryById(Long boardId) {
        return boardCategoryRepository.findById(boardId)
            .orElseThrow(() -> {
                throw new IllegalStateException("식별 번호 " + boardId + "에 해당하는 게시글이 존재하지 않습니다.");
            });
    }
}
