package dev.tolkach.methodologiesservice.adapter.out.persistence.specification;

import dev.tolkach.methodologiesservice.adapter.out.persistence.entity.ScaleQuestionEntity;
import dev.tolkach.methodologiesservice.application.model.ScaleQuestion;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ScaleQuestionSpecification {
    public static Specification<ScaleQuestionEntity> filterBy(ScaleQuestion filter) {
        return (Root<ScaleQuestionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }
            if (filter.getScaleId() != null) {
                predicates.add(cb.equal(root.get("scaleId"), filter.getScaleId()));
            }
            if (filter.getQuestionId() != null) {
                predicates.add(cb.equal(root.get("questionId"), filter.getQuestionId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
