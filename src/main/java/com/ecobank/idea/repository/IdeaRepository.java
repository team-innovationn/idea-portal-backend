package com.ecobank.idea.repository;

import com.ecobank.idea.dto.idea.IdeaStatisticsDTO;
import com.ecobank.idea.entity.idea.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long>, JpaSpecificationExecutor<Idea>  {
    @Query("SELECT new com.ecobank.idea.dto.idea.IdeaStatisticsDTO(" +
            "COUNT(i), " +
            "SUM(CASE WHEN i.status = 'APPROVED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN i.status = 'REJECTED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN i.status = 'PENDING' THEN 1 ELSE 0 END)) " +
            "FROM Idea i")
    IdeaStatisticsDTO getIdeaStatistics();

}
