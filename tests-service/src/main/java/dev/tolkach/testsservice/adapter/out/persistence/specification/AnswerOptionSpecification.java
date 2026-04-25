package dev.tolkach.testsservice.adapter.out.persistence.specification;

import dev.tolkach.testsservice.adapter.out.persistence.entity.AnswerOptionEntity;
import dev.tolkach.testsservice.application.model.AnswerOption;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AnswerOptionSpecification {
    public static Specification<AnswerOptionEntity> filterBy(AnswerOption filter) {
        return (Root<AnswerOptionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }
            if (filter.getQuestionId() != null) {
                predicates.add(cb.equal(root.get("questionId"), filter.getQuestionId()));
            }
            if (filter.getText() != null && !filter.getText().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("text")), "%" + filter.getText().toLowerCase() + "%"));
            }
            if (filter.getScore() != null) {
                predicates.add(cb.equal(root.get("score"), filter.getScore()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
