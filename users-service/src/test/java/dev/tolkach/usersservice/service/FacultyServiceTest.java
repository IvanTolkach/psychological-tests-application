package dev.tolkach.usersservice.service;

import dev.tolkach.usersservice.application.model.Faculty;
import dev.tolkach.usersservice.application.port.out.FacultyRepository;
import dev.tolkach.usersservice.application.service.FacultyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {

    @Mock
    FacultyRepository facultyRepository;

    @InjectMocks
    FacultyService service;

    @Test
    void getFacultiesByFilter_success() {

        Faculty filter = new Faculty();

        List<Faculty> faculties = List.of(new Faculty(), new Faculty());

        when(facultyRepository.findByFilter(filter))
                .thenReturn(faculties);

        List<Faculty> result = service.getFacultiesByFilter(filter);

        assertEquals(2, result.size());
    }
}
