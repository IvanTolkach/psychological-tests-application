package dev.tolkach.usersservice.adapter.out.security;

import dev.tolkach.usersservice.application.port.out.PasswordPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordAdapter implements PasswordPort {

    private final PasswordEncoder passwordEncoder;

    public PasswordAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public boolean isStrong(String password, String sname, String email, String phoneNumber) {
        if (password == null || password.length() < 12) {
            return false;
        }

        boolean hasUpper    = false;
        boolean hasLower    = false;
        boolean hasDigit    = false;
        boolean hasSpecial  = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))   hasUpper   = true;
            else if (Character.isLowerCase(c)) hasLower   = true;
            else if (Character.isDigit(c))     hasDigit   = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial) {
            return false;
        }

        int repeatCount = 1;
        char prev = password.charAt(0);
        for (int i = 1; i < password.length(); i++) {
            char current = password.charAt(i);
            if (current == prev) {
                repeatCount++;
                if (repeatCount >= 4) {
                    return false;
                }
            } else {
                repeatCount = 1;
            }
            prev = current;
        }

        String keyboardSeqs = "qwertyuiopasdfghjklzxcvbnm1234567890!@#$%^&*()";
        String reversed = new StringBuilder(keyboardSeqs).reverse().toString();

        String lowerPass = password.toLowerCase();
        for (int i = 0; i < keyboardSeqs.length() - 3; i++) {
            String seq = keyboardSeqs.substring(i, i + 4);
            String revSeq = reversed.substring(reversed.length() - (i + 4), reversed.length() - i);

            if (lowerPass.contains(seq) || lowerPass.contains(revSeq)) {
                return false;
            }
        }

        String lowerSname = (sname != null ? sname.toLowerCase() : "");
        String lowerEmail = (email != null ? email.toLowerCase() : "");

        String[] weakPatterns = {
                "password", "123456", "qwerty", "admin", "пароль", "йцукен",
                "12345678", "parol", "123123", "privet", "abc123", "111111",
                "qwerty123", "password1", "admin123", "йцукен123", "bntu", "бнту",
                lowerSname, lowerEmail.split("@")[0], phoneNumber.substring(1)
        };

        for (String weak : weakPatterns) {
            if (weak != null && !weak.isEmpty() && lowerPass.contains(weak)) {
                return false;
            }
        }

        return true;
    }
}
