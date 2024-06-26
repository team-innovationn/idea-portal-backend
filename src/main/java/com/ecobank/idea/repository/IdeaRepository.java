package com.ecobank.idea.repository;

import com.ecobank.idea.constants.IdeaEnums;
import com.ecobank.idea.entity.Idea;
import com.ecobank.idea.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // Get All Ideas by status
    Page<Idea> findByStatus(IdeaEnums.Status status, Pageable pageable);

//    @Query("SELECT u, COUNT(i) as ideaCount FROM Idea i JOIN i.user u WHERE i.status = :status GROUP BY u ORDER BY ideaCount DESC")
//    Page<Object[]> findUsersWithMostApprovedIdeas(@Param("status") IdeaEnums.Status status, Pageable pageable);

//    @Query("SELECT u, COUNT(i) as ideaCount FROM Idea i JOIN i.user u WHERE i.status = :status GROUP BY u ORDER BY ideaCount DESC")
//    Page<User> findUsersWithMostIdeas(@Param("status") IdeaEnums.Status status, Pageable pageable);
}
