package dev.tolkach.usersservice.application.service;

import dev.tolkach.usersservice.application.model.Faculty;
import dev.tolkach.usersservice.application.port.in.FacultyUseCase;
import dev.tolkach.usersservice.application.port.out.FacultyRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class FacultyService implements FacultyUseCase {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Faculty> getFacultiesByFilter(Faculty filter) {
        return facultyRepository.findByFilter(filter);
    }
}
