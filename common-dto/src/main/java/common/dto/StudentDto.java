package common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class StudentDto {
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
