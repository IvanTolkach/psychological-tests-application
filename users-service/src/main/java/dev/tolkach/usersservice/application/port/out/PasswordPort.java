package dev.tolkach.usersservice.application.port.out;

public interface PasswordPort {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
    boolean isStrong(String password, String sname, String email, String phoneNumber);
}