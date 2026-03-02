package dev.tolkach.attemptsservice.adapter.out.client;

import dev.tolkach.attemptsservice.application.port.out.MethodologiesPort;
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
    public void validateScaleExists(UUID scaleId) {
        Object response = methodologiesClient.getScale(scaleId);
        if (response == null) {
            throw new NoSuchElementException("Scale not found with id: " + scaleId);
        }
    }
}
