package dev.tolkach.attemptsservice.adapter.out.persistence.specification;

import dev.tolkach.attemptsservice.adapter.out.persistence.entity.TestAttemptEntity;
import dev.tolkach.attemptsservice.application.model.TestAttemptFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TestAttemptSpecification {
    public static Specification<TestAttemptEntity> filterBy(TestAttemptFilter filter) {
        return (Root<TestAttemptEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }
            if (filter.getStudentId() != null) {
                predicates.add(cb.equal(root.get("studentId"), filter.getStudentId()));
            }
            if (filter.getTestId() != null) {
                predicates.add(cb.equal(root.get("testId"), filter.getTestId()));
            }
            if (filter.getAttemptDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("attemptDate"), filter.getAttemptDateFrom()));
            }
            if (filter.getAttemptDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("attemptDate"), filter.getAttemptDateTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
