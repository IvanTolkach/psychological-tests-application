package dev.tolkach.testsservice.adapter.out.client;

import dev.tolkach.testsservice.application.port.out.UsersPort;
import feign.FeignException;
import org.springframework.security.access.AccessDeniedException;
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
        try {
            usersClient.getAdmin(adminId);
        } catch (FeignException.NotFound e) {
            throw new NoSuchElementException("Admin not found with id: " + adminId);
        } catch (FeignException.Unauthorized e) {
            throw new AccessDeniedException("You have no permission to execute this operation with Admin's parameters");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
