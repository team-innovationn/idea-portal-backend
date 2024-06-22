package com.ecobank.idea.repository;

import com.ecobank.idea.entity.Challenge;
import com.ecobank.idea.entity.Idea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    /**
     * @param title       - Title of idea
     * @param content - description
     * @param pageable    - pagination
     * @return - return ideas based on the filter conditions
     */
    Page<Challenge> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from Challenge e where e.id = ?1")
    int deleteChallengeById(Long id);
}
