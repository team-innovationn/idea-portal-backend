package com.ecobank.idea.repository;

import com.ecobank.idea.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    /**
     * @param userId
     * @param ideaId
     * @return True if an entry exits for a user and an idea - TO prevent voting twice for an idea
     */
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN TRUE ELSE FALSE END FROM Vote v WHERE v.user.id = :userId AND v.idea.id = :ideaId")
    boolean existsByUserIdAndIdeaId(@Param("userId") Long userId, @Param("ideaId") Long ideaId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.user.id = :userId AND v.idea.id = :ideaId")
    int deleteByUserIdAndIdeaId(@Param("userId") Long userId, @Param("ideaId") Long ideaId);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.idea.id = :ideaId")
    Optional<Vote> findByUserIdAndIdeaId(@Param("userId") Long userId, @Param("ideaId") Long ideaId);


}
