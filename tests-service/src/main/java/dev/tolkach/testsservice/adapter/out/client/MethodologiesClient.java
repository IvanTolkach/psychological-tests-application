package dev.tolkach.testsservice.adapter.out.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "methodologies-service")
public interface MethodologiesClient {

    @GetMapping("/api/methodologies/{id}")
    Object getMethodology(@PathVariable UUID id);  // можно вернуть DTO, если создадите
}