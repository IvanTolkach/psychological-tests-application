package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.specification;

import dev.tolkach.psychologicalTestsApplication.domain.model.Scale;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.ScaleEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ScaleSpecification {
    public static Specification<ScaleEntity> filterBy(Scale filter) {
        return (Root<ScaleEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }
            if (filter.getMethodologyId() != null) {
                predicates.add(cb.equal(root.get("methodologyId"), filter.getMethodologyId()));
            }
            if (filter.getName() != null && !filter.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
            }
            if (filter.getIsTotal() != null) {
                predicates.add(cb.equal(root.get("isTotal"), filter.getIsTotal()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
