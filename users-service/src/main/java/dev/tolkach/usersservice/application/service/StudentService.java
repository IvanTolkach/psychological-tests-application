package dev.tolkach.usersservice.application.service;

import dev.tolkach.usersservice.application.model.Student;
import dev.tolkach.usersservice.application.port.in.StudentUseCase;
import dev.tolkach.usersservice.application.port.out.FacultyRepository;
import dev.tolkach.usersservice.application.port.out.StudentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class StudentService implements StudentUseCase {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudentsByFilter(Student filter) {
        if (filter.getFacultyId() != null) {
            facultyRepository.findById(filter.getFacultyId())
                    .orElseThrow(() -> new NoSuchElementException("Faculty not found with id: " + filter.getFacultyId()));
        }
        return studentRepository.findByFilter(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + id));
    }

    @Override
    @Transactional
    public Student createUpdateStudent(Student student) {
        facultyRepository.findById(student.getFacultyId())
                .orElseThrow(() -> new NoSuchElementException("Faculty not found with id: " + student.getFacultyId()));

        if (student.getId() == null) {
            List<Student> existingStudents = studentRepository.findByFilter(student);
            if (!existingStudents.isEmpty()) {
                return existingStudents.getFirst();
            }
            return studentRepository.save(student);
        } else {
            Student existing = studentRepository.findById(student.getId())
                    .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + student.getId()));

            existing.setSname(student.getSname());
            existing.setFname(student.getFname());
            existing.setMname(student.getMname());
            existing.setFacultyId(student.getFacultyId());
            existing.setGroupNumber(student.getGroupNumber());
            existing.setGender(student.getGender());
            existing.setAge(student.getAge());
            existing.setResidence(student.getResidence());
            return studentRepository.save(existing);
        }
    }

    @Override
    @Transactional
    public void deleteStudent(UUID id) {
        studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + id));
        studentRepository.deleteById(id);
    }
}
