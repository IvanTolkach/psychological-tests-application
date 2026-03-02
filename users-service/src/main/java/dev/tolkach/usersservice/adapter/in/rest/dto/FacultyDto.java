package dev.tolkach.usersservice.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class FacultyDto {
    private UUID id;

    @NotBlank(message = "Faculty name cannot be blank")
    private String name;
}
