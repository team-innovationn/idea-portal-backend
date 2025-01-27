package com.ecobank.idea.repository;

import com.ecobank.idea.entity.idea.IdeaVertical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdeaVerticalRepository extends JpaRepository<IdeaVertical, Long> {
    Optional<IdeaVertical> findByName(String name);
}
