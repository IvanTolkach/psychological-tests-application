package dev.tolkach.usersservice.adapter.out.persistence.specification;

import dev.tolkach.usersservice.adapter.out.persistence.entity.StudentEntity;
import dev.tolkach.usersservice.application.model.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {
    public static Specification<StudentEntity> filterBy(Student filter) {
        return (Root<StudentEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }
            if (filter.getSname() != null) {
                predicates.add(cb.like(cb.lower(root.get("sname")), "%" + filter.getSname().toLowerCase() + "%"));
            }
            if (filter.getFname() != null) {
                predicates.add(cb.like(cb.lower(root.get("fname")), "%" + filter.getFname().toLowerCase() + "%"));
            }
            if (filter.getMname() != null) {
                predicates.add(cb.like(cb.lower(root.get("mname")), "%" + filter.getMname().toLowerCase() + "%"));
            }
            if (filter.getFacultyId() != null) {
                predicates.add(cb.equal(root.get("facultyId"), filter.getFacultyId()));
            }
            if (filter.getGroupNumber() != null) {
                predicates.add(cb.equal(root.get("groupNumber"), filter.getGroupNumber()));
            }
            if (filter.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), filter.getGender()));
            }
            if (filter.getAge() != null) {
                predicates.add(cb.equal(root.get("age"), filter.getAge()));
            }
            if (filter.getResidence() != null) {
                predicates.add(cb.like(cb.lower(root.get("residence")), "%" + filter.getResidence().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
