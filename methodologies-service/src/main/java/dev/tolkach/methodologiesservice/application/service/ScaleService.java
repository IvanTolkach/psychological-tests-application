package dev.tolkach.methodologiesservice.application.service;

import dev.tolkach.methodologiesservice.application.model.Scale;
import dev.tolkach.methodologiesservice.application.port.in.ScaleUseCase;
import dev.tolkach.methodologiesservice.application.port.out.MethodologyRepository;
import dev.tolkach.methodologiesservice.application.port.out.ScaleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ScaleService implements ScaleUseCase {

    private final ScaleRepository scaleRepository;
    private final MethodologyRepository methodologyRepository;

    public ScaleService(ScaleRepository scaleRepository, MethodologyRepository methodologyRepository) {
        this.scaleRepository = scaleRepository;
        this.methodologyRepository = methodologyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Scale> getScalesByFilter(Scale filter) {
        if (filter.getMethodologyId() != null) {
            methodologyRepository.findById(filter.getMethodologyId())
                    .orElseThrow(() -> new NoSuchElementException("Methodology not found with id: " + filter.getMethodologyId()));
        }
        return scaleRepository.findByFilter(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public Scale getScaleById(UUID id) {
        return scaleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Scale not found with id: " + id));
    }

    @Override
    @Transactional
    public Scale createUpdateScale(Scale scale) {
        methodologyRepository.findById(scale.getMethodologyId())
                .orElseThrow(() -> new NoSuchElementException("Methodology not found with id: " + scale.getMethodologyId()));

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

        if (Boolean.TRUE.equals(scale.getIsTotal())) {
            checkFilter.setName(null);
            checkFilter.setIsTotal(true);

            List<Scale> existingTotalScales = scaleRepository.findByFilter(checkFilter);

            boolean hasAnotherTotal = existingTotalScales.stream()
                    .anyMatch(s -> !s.getId().equals(scale.getId()));

            if (hasAnotherTotal) {
                throw new IllegalArgumentException(
                        "Methodology " + scale.getMethodologyId() + " already has a total scale (isTotal = true). " +
                                "Only one total scale per methodology is allowed."
                );
            }
        }

        if (scale.getId() == null) {
            return scaleRepository.save(scale);
        } else {
            Scale existing = scaleRepository.findById(scale.getId())
                    .orElseThrow(() -> new NoSuchElementException("Scale not found with id: " + scale.getId()));

            existing.setName(scale.getName());
            existing.setMethodologyId(scale.getMethodologyId());
            existing.setIsTotal(scale.getIsTotal());
            existing.setDescription(scale.getDescription());

            return scaleRepository.save(existing);
        }
    }

    @Override
    @Transactional
    public void deleteScale(UUID id) {
        if (scaleRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Scale not found with id: " + id);
        }
        scaleRepository.deleteById(id);
    }
}
