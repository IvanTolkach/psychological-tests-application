package dev.tolkach.testsservice.adapter.out.client;

import dev.tolkach.testsservice.application.port.out.MethodologiesPort;
import feign.FeignException;
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
        try {
            methodologiesClient.getMethodology(methodologyId);
        } catch (FeignException.NotFound e) {
            throw new NoSuchElementException("Methodology not found with id: " + methodologyId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

