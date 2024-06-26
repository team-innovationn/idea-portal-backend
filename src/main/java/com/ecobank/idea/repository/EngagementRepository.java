package com.ecobank.idea.repository;

import com.ecobank.idea.constants.EngagementEnum;
import com.ecobank.idea.entity.Engagement;
import com.ecobank.idea.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface EngagementRepository extends JpaRepository<Engagement, Long> {
    @Query("SELECT e.idea FROM Engagement e GROUP BY e.idea ORDER BY COUNT(e) DESC")
    List<Idea> findIdeaWithMostEngagements(Pageable pageable);

    @Query("SELECT e FROM Engagement e WHERE e.idea.id = :ideaId AND e.user.id = :userId AND e.engagementType = :engagementType")
    Optional<Engagement> findEngagementByIdeaIdUserIdAndEngagementType(@Param("ideaId") Long ideaId, @Param("userId") Long userId, @Param("engagementType") EngagementEnum engagementType);
}
