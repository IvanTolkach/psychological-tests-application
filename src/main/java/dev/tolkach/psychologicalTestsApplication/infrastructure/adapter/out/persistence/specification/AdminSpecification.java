package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.specification;

import dev.tolkach.psychologicalTestsApplication.domain.model.Admin;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.AdminEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AdminSpecification {
    public static Specification<AdminEntity> filterBy(Admin filter) {
        return (Root<AdminEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }
            if (filter.getSname() != null && !filter.getSname().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("sname")), "%" + filter.getSname().toLowerCase() + "%"));
            }
            if (filter.getFname() != null && !filter.getFname().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("fname")), "%" + filter.getFname().toLowerCase() + "%"));
            }
            if (filter.getEmail() != null && !filter.getEmail().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + filter.getEmail().toLowerCase() + "%"));
            }
            if (filter.getPhoneNumber() != null && !filter.getPhoneNumber().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("phoneNumber")), "%" + filter.getPhoneNumber().toLowerCase() + "%"));
            }
            if (filter.getRole() != null) {
                predicates.add(cb.equal(root.get("role"), filter.getRole()));
            }
            if (filter.getIsActive() != null) {
                predicates.add(cb.equal(root.get("isActive"), filter.getIsActive()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
