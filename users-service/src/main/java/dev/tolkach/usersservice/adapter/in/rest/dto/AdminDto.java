package dev.tolkach.usersservice.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.tolkach.usersservice.application.model.AdminRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AdminDto {
    private UUID id;

    @NotBlank(message = "Sname cannot be blank")
    @Size(max = 50)
    private String sname;

    @NotBlank(message = "Fname cannot be blank")
    @Size(max = 50)
    private String fname;

    @Size(max = 50)
    private String mname;

    @Email(message = "Invalid email format")
    @Size(max = 255)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 12, message = "New password must be at least 12 characters")
    private String password;

    @Size(max = 20)
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    private AdminRole role;

    private Boolean isActive;
}
