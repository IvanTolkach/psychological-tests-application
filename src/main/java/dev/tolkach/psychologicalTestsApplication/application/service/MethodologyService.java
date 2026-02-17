package dev.tolkach.psychologicalTestsApplication.application.service;

import dev.tolkach.psychologicalTestsApplication.domain.model.Methodology;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.MethodologyUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.MethodologyRepository;

import java.util.List;
import java.util.UUID;

public class MethodologyService implements MethodologyUseCase {

    private final MethodologyRepository methodologyRepository;

    public MethodologyService(MethodologyRepository methodologyRepository) {
        this.methodologyRepository = methodologyRepository;
    }

    @Override
    public List<Methodology> getMethodologiesByFilter(Methodology filter) {
        return methodologyRepository.findByFilter(filter);
    }

    @Override
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
                    .orElseThrow(() -> new IllegalArgumentException("Methodology not found with id: " + methodology.getId()));

            existing.setName(methodology.getName());
            existing.setDescription(methodology.getDescription());

            return methodologyRepository.save(existing);
        }
    }

    @Override
    public void deleteMethodology(UUID id) {
        if (methodologyRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Methodology not found with id: " + id);
        }
        methodologyRepository.deleteById(id);
    }
}
