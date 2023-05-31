package com.sharetravel.domain.user.repository;

import com.sharetravel.domain.user.entity.User;
import com.sharetravel.global.auth.oauth2.dto.OAuth2Provider;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(value = "User.withAll")
    Optional<User> findById(Long id);

    Optional<User> findByEmailAndProvider(String email, OAuth2Provider provider);

    Optional<User> findByNickName(String nickName);

    @Modifying
    @Query("delete from User u where u.id = :id")
    void deleteById(@Param("id") Long id);
}
