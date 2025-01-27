package com.ecobank.idea.repository;

import com.ecobank.idea.entity.ValueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValueTypeRepository extends JpaRepository<ValueType, Long> {
    Optional<ValueType> findByName(String name);
}
