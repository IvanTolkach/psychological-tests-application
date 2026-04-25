package dev.tolkach.usersservice.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class PasswordChangeDto {
    @NotNull(message = "Admin Id cannot be blank")
    private UUID adminId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Old password cannot be blank")
    private String oldPassword;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "New password cannot be blank")
    @Size(min = 12, message = "New password must be at least 12 characters")
    private String newPassword;
}
