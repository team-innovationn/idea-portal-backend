package com.ecobank.idea.repository;

import com.ecobank.idea.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Transactional
    @Modifying
    @Query("delete from Comment e where e.id = ?1")
    int deleteCommentById(Long id);

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.replies WHERE c.idea.id = :ideaId AND c.parentComment IS NULL")
    Page<Comment> findByIdea_IdeaId(@Param("ideaId") Long ideaId, Pageable pageable);

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.replies WHERE c.idea.id = :ideaId AND c.parentComment IS NULL")
    List<Comment> findCommentsByIdeaIdAndParentIsNull(@Param("ideaId") Long ideaId);
}
