package dev.tolkach.usersservice.adapter.out;

import dev.tolkach.usersservice.adapter.out.security.PasswordAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordAdapterTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    PasswordAdapter adapter;

    @Test
    void encode_callsEncoder() {
        when(passwordEncoder.encode("raw")).thenReturn("encoded");
        assertEquals("encoded", adapter.encode("raw"));
    }

    @Test
    void matches_callsEncoder() {
        when(passwordEncoder.matches("raw", "enc")).thenReturn(true);
        assertTrue(adapter.matches("raw", "enc"));
    }

    @Test
    void isStrong_variousCases() {
        String phone = "0123456789";

        assertFalse(adapter.isStrong("short", null, null, phone));
        assertFalse(adapter.isStrong("aa1!aa1!aa1!", null, null, phone));
        assertFalse(adapter.isStrong("AA1!AA1!AA1!", null, null, phone));
        assertFalse(adapter.isStrong("Aa!Aa!Aa!Aa!", null, null, phone));
        assertFalse(adapter.isStrong("Aa1Aa1Aa1Aa1", null, null, phone));
        assertFalse(adapter.isStrong("AAAAa1!Bb2@Cc3#", null, null, phone));
        assertFalse(adapter.isStrong("qwer1234!Aa", null, null, phone));
        assertFalse(adapter.isStrong("myname123!Aa", "MyName", null, phone));
        assertFalse(adapter.isStrong("user123!Aa", null, "user@mail.com", phone));
        assertFalse(adapter.isStrong("2345678901Aa!", null, null, phone));
        assertTrue(adapter.isStrong("Aa1!Bb2@Cc3#", "Sname", "email@mail.com", phone));
    }
}
