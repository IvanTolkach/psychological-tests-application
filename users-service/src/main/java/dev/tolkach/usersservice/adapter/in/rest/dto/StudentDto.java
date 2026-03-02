package dev.tolkach.usersservice.adapter.in.rest.dto;

import dev.tolkach.usersservice.application.model.Gender;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class StudentDto {
    private UUID id;

    @NotBlank(message = "Sname cannot be blank")
    private String sname;

    @NotBlank(message = "Fname cannot be blank")
    private String fname;

    private String mname;

    @NotNull(message = "Faculty ID cannot be null")
    private UUID facultyId;

    @NotNull(message = "Group number cannot be null")
    @Min(value = 0, message = "Group number must be at least 0")
    @Max(value = 99999999, message = "Group number must be at most 99999999")
    private Integer groupNumber;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @NotNull(message = "Age cannot be null")
    @Min(value = 1, message = "Age must be greater than 0")
    @Max(value = 120, message = "Age must be at most 120")
    private Integer age;

    @NotBlank(message = "Residence cannot be blank")
    private String residence;
}
