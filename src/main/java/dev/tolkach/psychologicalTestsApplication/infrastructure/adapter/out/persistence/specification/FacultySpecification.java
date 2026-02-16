package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.specification;

import dev.tolkach.psychologicalTestsApplication.domain.model.Faculty;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.FacultyEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FacultySpecification {
    public static Specification<FacultyEntity> filterBy(Faculty filter) {
        return (Root<FacultyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }
            if (filter.getName() != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
