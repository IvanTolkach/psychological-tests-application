package dev.tolkach.usersservice.application.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Student {
    private UUID id;
    private String sname;
    private String fname;
    private String mname;
    private UUID facultyId;
    private Integer groupNumber;
    private Gender gender;
    private Integer age;
    private String residence;
}
