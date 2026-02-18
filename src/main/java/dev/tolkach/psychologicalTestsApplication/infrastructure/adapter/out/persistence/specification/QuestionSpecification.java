package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.specification;

import dev.tolkach.psychologicalTestsApplication.domain.model.Question;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.QuestionEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class QuestionSpecification {
    public static Specification<QuestionEntity> filterBy(Question filter) {
        return (Root<QuestionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }
            if (filter.getTestId() != null) {
                predicates.add(cb.equal(root.get("testId"), filter.getTestId()));
            }
            if (filter.getText() != null && !filter.getText().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("text")), "%" + filter.getText().toLowerCase() + "%"));
            }
            if (filter.getType() != null) {
                predicates.add(cb.equal(root.get("type"), filter.getType()));
            }
            if (filter.getPosition() != null) {
                predicates.add(cb.equal(root.get("position"), filter.getPosition()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
