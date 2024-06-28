package com.ecobank.idea.repository;

import com.ecobank.idea.entity.idea.IdeaVertical;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaVerticalRepository extends CrudRepository<IdeaVertical, Long> {
}
