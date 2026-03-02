package dev.tolkach.attemptsservice.adapter.out.persistence.specification;

import dev.tolkach.attemptsservice.adapter.out.persistence.entity.TestAttemptScoreEntity;
import dev.tolkach.attemptsservice.application.model.TestAttemptScore;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TestAttemptScoreSpecification {
    public static Specification<TestAttemptScoreEntity> filterBy(TestAttemptScore filter) {
        return (Root<TestAttemptScoreEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }
            if (filter.getTestAttemptId() != null) {
                predicates.add(cb.equal(root.get("testAttemptId"), filter.getTestAttemptId()));
            }
            if (filter.getScaleId() != null) {
                predicates.add(cb.equal(root.get("scaleId"), filter.getScaleId()));
            }
            if (filter.getScore() != null) {
                predicates.add(cb.equal(root.get("score"), filter.getScore()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
