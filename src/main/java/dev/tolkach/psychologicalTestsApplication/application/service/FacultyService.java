package dev.tolkach.psychologicalTestsApplication.application.service;

import dev.tolkach.psychologicalTestsApplication.domain.model.Faculty;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.FacultyUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.FacultyRepository;

import java.util.List;

public class FacultyService implements FacultyUseCase {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public List<Faculty> getFacultiesByFilter(Faculty filter) {
        return facultyRepository.findByFilter(filter);
    }
}
