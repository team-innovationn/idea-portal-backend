package com.ecobank.idea.repository;

import com.ecobank.idea.constants.IdeaEnums;
import com.ecobank.idea.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u ORDER BY u.interactionCount DESC")
    Optional<List<User>> findUsersWithMostInteractions();

    Page<User> findAll(Pageable pageable);

    // Method to search users by first name and last name
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<User> searchByFirstNameOrLastName(@Param("name") String name, Pageable pageable);

    // Sort Users by interactions
    Page<User> findAllByOrderByInteractionCountDesc(Pageable pageable);
//
//    @Query("SELECT u FROM User u JOIN u.ideas i WHERE i.approved = true GROUP BY u ORDER BY COUNT(i) DESC")
//    List<User> findUsersWithMostApprovedIdeas(@Param("status") IdeaEnums.Status status, Pageable pageable);
}
