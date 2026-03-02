package dev.tolkach.testsservice.adapter.out.client;

import dev.tolkach.testsservice.application.port.out.UsersPort;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class UsersClientAdapter implements UsersPort {

    private final UsersClient usersClient;

    public UsersClientAdapter(UsersClient usersClient) {
        this.usersClient = usersClient;
    }

    @Override
    public void validateAdminExists(UUID adminId) {
        Object response = usersClient.getAdmin(adminId);
        if (response == null) {
            throw new NoSuchElementException("Admin not found with id: " + adminId);
        }
    }
}
