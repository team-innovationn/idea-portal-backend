package com.ecobank.idea.repository;

import com.ecobank.idea.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Transactional
    @Modifying
    @Query("delete from Comment e where e.id = ?1")
    int deleteCommentById(Long id);

    //    Optional<List<Comment>> findByIdea_IdeaId(Long ideaId);
    Page<Comment> findByIdea_IdeaId(Long ideaId, Pageable pageable);
}
