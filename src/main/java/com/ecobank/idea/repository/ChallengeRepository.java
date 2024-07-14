package com.ecobank.idea.repository;

import com.ecobank.idea.entity.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    /**
     * @param title    - Title of idea
     * @param content  - description
     * @param pageable - pagination
     * @return - return ideas based on the filter conditions
     */
    Page<Challenge> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from Challenge e where e.id = ?1")
    int deleteChallengeById(Long id);

    /**
     * Fetch the single existing challenge.
     *
     * @return the challenge if it exists, otherwise Optional.empty()
     */
    @Query("select e from Challenge e")
    Optional<Challenge> findSingleChallenge();

    /**
     * Delete the single existing challenge.
     *
     * @return the number of entities deleted (should be 1 or 0)
     */
    @Transactional
    @Modifying
    @Query("delete from Challenge e")
    int deleteSingleChallenge();

    /**
     * Save or update the single challenge.
     * Ensures that there is always only one challenge.
     *
     * @param challenge the challenge entity to save or update
     * @return the saved or updated challenge
     */
    @Override
    @Transactional
    default <S extends Challenge> S save(S challenge) {
        deleteSingleChallenge(); // Ensure there's only one challenge
        return saveAndFlush(challenge); // Save the new challenge
    }
}
