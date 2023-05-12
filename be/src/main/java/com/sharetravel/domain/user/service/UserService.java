package com.sharetravel.domain.user.service;

import com.sharetravel.domain.user.dto.UserResponseDto;
import com.sharetravel.domain.user.dto.UserUpdateRequestDto;
import com.sharetravel.domain.user.entity.User;
import com.sharetravel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> {
                throw new IllegalStateException("식별 번호가 " + id + "에 해당되는 사용자가 없습니다.");
            });

        return UserResponseDto.from(user);
    }

    @Transactional
    public UserResponseDto update(Long id, UserUpdateRequestDto request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> {
                throw new IllegalStateException("식별 번호가 " + id + "에 해당되는 사용자가 없습니다.");
            });

        user.update(request.getNickName(), request.getPicture());

        return UserResponseDto.from(user);
    }
}
