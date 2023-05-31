package com.sharetravel.domain.user.repository;

import com.sharetravel.domain.user.entity.UserTravelKeyword;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserTravelKeywordRepository extends JpaRepository<UserTravelKeyword, Long> {

    @Modifying
    @Query("delete from UserTravelKeyword utk where utk.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);
}
