package dev.tolkach.psychologicalTestsApplication.domain.port.in;

import dev.tolkach.psychologicalTestsApplication.domain.model.Faculty;

import java.util.List;

public interface FacultyUseCase {
    List<Faculty> getFacultiesByFilter(Faculty filter);
}
