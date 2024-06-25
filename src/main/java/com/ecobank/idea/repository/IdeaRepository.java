package com.ecobank.idea.repository;

import com.ecobank.idea.entity.Idea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

    /**
     * @param title       - Title of idea
     * @param description - description
     * @param pageable    - pagination
     * @return - return ideas based on the filter conditions
     */
    Page<Idea> findByTitleContainingOrDescriptionContaining(String title, String description, Pageable pageable);

}
