package dev.tolkach.testsservice.adapter.out.client;

import dev.tolkach.testsservice.application.port.out.MethodologiesPort;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class MethodologiesClientAdapter implements MethodologiesPort {
    private final MethodologiesClient methodologiesClient;

    public MethodologiesClientAdapter(MethodologiesClient methodologiesClient) {
        this.methodologiesClient = methodologiesClient;
    }

    @Override
    public void validateMethodologyExists(UUID methodologyId) {
        Object response = methodologiesClient.getMethodology(methodologyId);
        if (response == null) {
            throw new NoSuchElementException("Methodology not found with id: " + methodologyId);
        }
    }
}

