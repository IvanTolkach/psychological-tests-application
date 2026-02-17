package dev.tolkach.psychologicalTestsApplication.application.service;

import dev.tolkach.psychologicalTestsApplication.domain.model.Scale;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.ScaleUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.MethodologyRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.ScaleRepository;

import java.util.List;
import java.util.UUID;

public class ScaleService implements ScaleUseCase {

    private final ScaleRepository scaleRepository;
    private final MethodologyRepository methodologyRepository;

    public ScaleService(ScaleRepository scaleRepository, MethodologyRepository methodologyRepository) {
        this.scaleRepository = scaleRepository;
        this.methodologyRepository = methodologyRepository;
    }

    @Override
    public List<Scale> getScalesByFilter(Scale filter) {
        if (filter.getMethodologyId() != null) {
            methodologyRepository.findById(filter.getMethodologyId())
                    .orElseThrow(() -> new IllegalArgumentException("Methodology not found with id: " + filter.getMethodologyId()));
        }
        return scaleRepository.findByFilter(filter);
    }

    @Override
    public Scale createUpdateScale(Scale scale) {
        methodologyRepository.findById(scale.getMethodologyId())
                .orElseThrow(() -> new IllegalArgumentException("Methodology not found with id: " + scale.getMethodologyId()));

        Scale checkFilter = new Scale();
        checkFilter.setMethodologyId(scale.getMethodologyId());
        checkFilter.setName(scale.getName());

        List<Scale> existingWithSameName = scaleRepository.findByFilter(checkFilter);

        boolean nameConflict = existingWithSameName.stream()
                .anyMatch(s -> !s.getId().equals(scale.getId()));

        if (nameConflict) {
            throw new IllegalArgumentException(
                    "Scale with name '" + scale.getName() + "' already exists for methodology " + scale.getMethodologyId()
            );
        }

        if (scale.getId() == null) {
            return scaleRepository.save(scale);
        } else {
            Scale existing = scaleRepository.findById(scale.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Scale not found with id: " + scale.getId()));

            existing.setName(scale.getName());
            existing.setMethodologyId(scale.getMethodologyId());
            existing.setIsTotal(scale.getIsTotal());
            existing.setDescription(scale.getDescription());

            return scaleRepository.save(existing);
        }
    }

    @Override
    public void deleteScale(UUID id) {
        if (scaleRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Scale not found with id: " + id);
        }
        scaleRepository.deleteById(id);
    }
}
