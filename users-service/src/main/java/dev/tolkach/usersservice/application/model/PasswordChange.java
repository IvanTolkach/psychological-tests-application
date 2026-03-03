package dev.tolkach.usersservice.application.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class PasswordChange {
    private UUID adminId;
    private String oldPassword;
    private String newPassword;
}
