package com.sharetravel.domain.user.repository;

import com.sharetravel.domain.user.entity.User;
import com.sharetravel.global.auth.oauth2.dto.OAuth2Provider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndProvider(String email, OAuth2Provider provider);

    Optional<User> findByNickName(String nickName);
}
