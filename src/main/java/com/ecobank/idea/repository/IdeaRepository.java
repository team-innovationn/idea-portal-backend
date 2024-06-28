package com.ecobank.idea.repository;

import com.ecobank.idea.constants.IdeaEnums;
import com.ecobank.idea.entity.idea.Idea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long>, JpaSpecificationExecutor<Idea>  {

    /**
     * @param title       - Title of idea
     * @param description - description
     * @param pageable    - pagination
     * @return - return ideas based on the filter conditions
     */
    Page<Idea> findByTitleContainingOrDescriptionContaining(String title, String description, Pageable pageable);

    // Get All Ideas by status
    Page<Idea> findByStatus(IdeaEnums.Status status, Pageable pageable);

    @Query("SELECT i FROM Idea i WHERE " +
            "(:valueTypeId IS NULL OR i.valueType.id = :valueTypeId) AND " +
            "(:verticalId IS NULL OR i.ideaVertical.id = :verticalId) AND " +
            "(:fromDate IS NULL OR i.createdAt >= :fromDate) AND " +
            "(:toDate IS NULL OR i.createdAt <= :toDate) AND " +
            "(:status IS NULL OR i.status = :status)")
    Page<Idea> findIdeasByFilter(
            @Param("valueTypeId") Long valueTypeId,
            @Param("verticalId") Long verticalId,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            @Param("status") IdeaEnums.Status status,
            Pageable pageable
    );


//    @Query("SELECT u, COUNT(i) as ideaCount FROM Idea i JOIN i.user u WHERE i.status = :status GROUP BY u ORDER BY ideaCount DESC")
//    Page<Object[]> findUsersWithMostApprovedIdeas(@Param("status") IdeaEnums.Status status, Pageable pageable);

//    @Query("SELECT u, COUNT(i) as ideaCount FROM Idea i JOIN i.user u WHERE i.status = :status GROUP BY u ORDER BY ideaCount DESC")
//    Page<User> findUsersWithMostIdeas(@Param("status") IdeaEnums.Status status, Pageable pageable);
}
