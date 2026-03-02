package dev.tolkach.testsservice.adapter.out.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "users-service")
public interface UsersClient {

    @GetMapping("/api/admins/{id}")
    Object getAdmin(@PathVariable UUID id);
}
