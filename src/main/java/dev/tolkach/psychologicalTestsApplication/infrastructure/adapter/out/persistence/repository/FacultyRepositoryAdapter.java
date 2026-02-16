package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.repository;

import dev.tolkach.psychologicalTestsApplication.domain.model.Faculty;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.FacultyRepository;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper.FacultyMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class FacultyRepositoryAdapter implements FacultyRepository {
    private final JpaFacultyRepository jpaFacultyRepository;
    private final FacultyMapper facultyMapper;

    public FacultyRepositoryAdapter(JpaFacultyRepository jpaFacultyRepository, FacultyMapper facultyMapper) {
        this.jpaFacultyRepository = jpaFacultyRepository;
        this.facultyMapper = facultyMapper;
    }

    @Override
    public Optional<Faculty> findById(UUID id) {
        return jpaFacultyRepository.findById(id)
                .map(facultyMapper::toDomain);
    }
}