package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.specification;

import dev.tolkach.psychologicalTestsApplication.domain.model.ScoreRange;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.ScoreRangeEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ScoreRangeSpecification {
    public static Specification<ScoreRangeEntity> filterBy(ScoreRange filter) {
        return (Root<ScoreRangeEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }
            if (filter.getScaleId() != null) {
                predicates.add(cb.equal(root.get("scaleId"), filter.getScaleId()));
            }
            if (filter.getMinScore() != null) {
                predicates.add(cb.equal(root.get("minScore"), filter.getMinScore()));
            }
            if (filter.getMaxScore() != null) {
                predicates.add(cb.equal(root.get("maxScore"), filter.getMaxScore()));
            }
            if (filter.getInterpretation() != null && !filter.getInterpretation().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("interpretation")), "%" + filter.getInterpretation().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
