package dev.tolkach.usersservice.application.port.in;

import dev.tolkach.usersservice.application.model.Faculty;

import java.util.List;

public interface FacultyUseCase {
    List<Faculty> getFacultiesByFilter(Faculty filter);
}
