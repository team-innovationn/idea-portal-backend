package com.ecobank.idea.entity.idea;

import com.ecobank.idea.constants.IdeaEnums;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Date;

public interface IdeaSpecification {
    static Specification<Idea> hasVerticalId(Long verticalId) {
        return (root, query, cb) -> verticalId == null ? null : cb.equal(root.get("ideaVertical").get("verticalId"), verticalId);
    }

    static Specification<Idea> hasValueTypeId(Long valueTypeId) {
        return (root, query, cb) -> valueTypeId == null ? null : cb.equal(root.get("valueType").get("valueTypeId"), valueTypeId);
    }

    static Specification<Idea> hasStatus(IdeaEnums.Status status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    static Specification<Idea> createdAfter(LocalDateTime fromDate) {
        return (root, query, cb) -> fromDate == null ? null : cb.greaterThanOrEqualTo(root.get("createdDate"), fromDate);
    }

    static Specification<Idea> createdBefore(LocalDateTime toDate) {
        return (root, query, cb) -> toDate == null ? null : cb.lessThanOrEqualTo(root.get("createdDate"), toDate);
    }
}