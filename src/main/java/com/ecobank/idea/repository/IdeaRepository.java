package com.ecobank.idea.repository;

import com.ecobank.idea.dto.idea.IdeaStatisticsDTO;
import com.ecobank.idea.entity.idea.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long>, JpaSpecificationExecutor<Idea>  {
    @Query("SELECT COUNT(i) FROM Idea i")
    Long countTotalIdeas();

    @Query("SELECT COUNT(i) FROM Idea i WHERE i.status = 'PENDING'")
    Long countPendingIdeas();

    @Query("SELECT COUNT(i) FROM Idea i WHERE i.status = 'APPROVED'")
    Long countApprovedIdeas();

    @Query("SELECT COUNT(i) FROM Idea i WHERE i.status = 'REJECTED'")
    Long countRejectedIdeas();

}
