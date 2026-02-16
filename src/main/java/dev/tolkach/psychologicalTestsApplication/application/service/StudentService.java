package dev.tolkach.psychologicalTestsApplication.application.service;

import dev.tolkach.psychologicalTestsApplication.domain.model.Student;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.StudentUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.FacultyRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.StudentRepository;

import java.util.List;
import java.util.UUID;

public class StudentService implements StudentUseCase {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    @Override
    public List<Student> getStudentsByFilter(Student filter) {
        if (filter.getFacultyId() != null) {
            facultyRepository.findById(filter.getFacultyId())
                    .orElseThrow(() -> new IllegalArgumentException("Faculty not found with id: " + filter.getFacultyId()));
        }
        return studentRepository.findByFilter(filter);
    }

    @Override
    public Student createUpdateStudent(Student student) {
        facultyRepository.findById(student.getFacultyId())
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found with id: " + student.getFacultyId()));

        if (student.getId() == null) {
            List<Student> existingStudents = studentRepository.findByFilter(student);
            if (!existingStudents.isEmpty()) {
                return existingStudents.getFirst();
            }
            return studentRepository.save(student);
        } else {
            Student existing = studentRepository.findById(student.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + student.getId()));

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
    public void deleteStudent(UUID id) {
        studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + id));
        studentRepository.deleteById(id);
    }
}
