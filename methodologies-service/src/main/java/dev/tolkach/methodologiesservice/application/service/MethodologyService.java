package dev.tolkach.methodologiesservice.application.service;

import dev.tolkach.methodologiesservice.application.model.Methodology;
import dev.tolkach.methodologiesservice.application.port.in.MethodologyUseCase;
import dev.tolkach.methodologiesservice.application.port.out.MethodologyRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class MethodologyService implements MethodologyUseCase {

    private final MethodologyRepository methodologyRepository;

    public MethodologyService(MethodologyRepository methodologyRepository) {
        this.methodologyRepository = methodologyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Methodology> getMethodologiesByFilter(Methodology filter) {
        return methodologyRepository.findByFilter(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public Methodology getMethodologyById(UUID id) {
        return methodologyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Methodology not found with id: " + id));
    }

    @Override
    @Transactional
    public Methodology createUpdateMethodology(Methodology methodology) {
        methodologyRepository.findByFilter(new Methodology() {{
                    setName(methodology.getName());
                }}).stream()
                .filter(m -> !m.getId().equals(methodology.getId()))
                .findAny()
                .ifPresent(m -> {
                    throw new IllegalArgumentException("Methodology with name '" + methodology.getName() + "' already exists");
                });

        if (methodology.getId() == null) {
            return methodologyRepository.save(methodology);
        } else {
            Methodology existing = methodologyRepository.findById(methodology.getId())
                    .orElseThrow(() -> new NoSuchElementException("Methodology not found with id: " + methodology.getId()));

            existing.setName(methodology.getName());
            existing.setDescription(methodology.getDescription());

            return methodologyRepository.save(existing);
        }
    }

    @Override
    @Transactional
    public void deleteMethodology(UUID id) {
        if (methodologyRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Methodology not found with id: " + id);
        }
        methodologyRepository.deleteById(id);
    }
}
