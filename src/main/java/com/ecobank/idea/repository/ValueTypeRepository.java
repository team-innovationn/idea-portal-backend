package com.ecobank.idea.repository;

import com.ecobank.idea.entity.ValueType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValueTypeRepository extends CrudRepository<ValueType, Long> {
}
