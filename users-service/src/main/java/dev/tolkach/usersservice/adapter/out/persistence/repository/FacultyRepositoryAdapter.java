package dev.tolkach.usersservice.adapter.out.persistence.repository;

import dev.tolkach.usersservice.adapter.out.persistence.mapper.FacultyMapper;
import dev.tolkach.usersservice.adapter.out.persistence.specification.FacultySpecification;
import dev.tolkach.usersservice.application.model.Faculty;
import dev.tolkach.usersservice.application.port.out.FacultyRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FacultyRepositoryAdapter implements FacultyRepository {
    private final JpaFacultyRepository jpaFacultyRepository;
    private final FacultyMapper facultyMapper;

    public FacultyRepositoryAdapter(JpaFacultyRepository jpaFacultyRepository, FacultyMapper facultyMapper) {
        this.jpaFacultyRepository = jpaFacultyRepository;
        this.facultyMapper = facultyMapper;
    }

    @Override
    public List<Faculty> findByFilter(Faculty filter) {
        return jpaFacultyRepository.findAll(FacultySpecification.filterBy(filter)).stream()
                .map(facultyMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Faculty> findById(UUID id) {
        return jpaFacultyRepository.findById(id)
                .map(facultyMapper::toDomain);
    }
}