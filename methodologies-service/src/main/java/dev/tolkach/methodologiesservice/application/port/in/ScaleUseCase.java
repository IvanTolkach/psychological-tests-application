package dev.tolkach.methodologiesservice.application.port.in;

import dev.tolkach.methodologiesservice.application.model.Scale;

import java.util.List;
import java.util.UUID;

public interface ScaleUseCase {
    List<Scale> getScalesByFilter(Scale filter);
    Scale getScaleById(UUID id);
    Scale createUpdateScale(Scale scale);
    void deleteScale(UUID id);
}
