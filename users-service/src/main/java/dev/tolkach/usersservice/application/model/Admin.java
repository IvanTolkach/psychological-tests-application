package dev.tolkach.usersservice.application.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Admin {
    private UUID id;
    private String sname;
    private String fname;
    private String mname;
    private String email;
    private String password;
    private String phoneNumber;
    private AdminRole role;
    private Boolean isActive;
}
