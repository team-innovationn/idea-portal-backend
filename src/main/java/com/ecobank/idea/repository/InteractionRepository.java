package com.ecobank.idea.repository;

import com.ecobank.idea.constants.InteractionEnum;
import com.ecobank.idea.entity.Interaction;
import com.ecobank.idea.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    @Query("SELECT i.user FROM Interaction i GROUP BY i.user ORDER BY COUNT(i) DESC")
    List<User> findUserWithMostInteractions(Pageable pageable);

    @Query("SELECT i FROM Interaction i WHERE i.idea.id = :ideaId AND i.user.id = :userId")
    Optional<Interaction> findInteractionByIdeaIdAndUserId(@Param("ideaId") Long ideaId, @Param("userId") Long userId);

    @Query("SELECT i FROM Interaction i WHERE i.idea.id = :ideaId AND i.user.id = :userId AND i.interactionType = :interactionType")
    Optional<Interaction> findInteractionByIdeaIdUserIdAndInteractionType(@Param("ideaId") Long ideaId, @Param("userId") Long userId, @Param("interactionType") InteractionEnum interactionType);
}
