package dev.tolkach.psychologicalTestsApplication.domain.port.in;

import dev.tolkach.psychologicalTestsApplication.domain.model.Scale;

import java.util.List;
import java.util.UUID;

public interface ScaleUseCase {
    List<Scale> getScalesByFilter(Scale filter);
    Scale createUpdateScale(Scale scale);
    void deleteScale(UUID id);
}
