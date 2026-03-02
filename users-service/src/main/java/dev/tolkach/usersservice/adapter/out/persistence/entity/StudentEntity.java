package dev.tolkach.usersservice.adapter.out.persistence.entity;

import dev.tolkach.usersservice.application.model.Gender;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String sname;

    @Column(nullable = false, length = 50)
    private String fname;

    @Column(length = 50)
    private String mname;

    @Column(name = "faculty_id", nullable = false)
    private UUID facultyId;

    @Column(name = "group_number", nullable = false)
    private Integer groupNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, length = 100)
    private String residence;
}
