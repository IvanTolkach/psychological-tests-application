package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.specification;

import dev.tolkach.psychologicalTestsApplication.domain.model.StudentAnswer;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.StudentAnswerEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentAnswerSpecification {
    public static Specification<StudentAnswerEntity> filterBy(StudentAnswer filter) {
        return (Root<StudentAnswerEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }
            if (filter.getTestAttemptId() != null) {
                predicates.add(cb.equal(root.get("testAttemptId"), filter.getTestAttemptId()));
            }
            if (filter.getQuestionId() != null) {
                predicates.add(cb.equal(root.get("questionId"), filter.getQuestionId()));
            }
            if (filter.getAnswerOptionId() != null) {
                predicates.add(cb.equal(root.get("answerOptionId"), filter.getAnswerOptionId()));
            }
            if (filter.getAnswerValue() != null && !filter.getAnswerValue().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("answerValue")), "%" + filter.getAnswerValue().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
