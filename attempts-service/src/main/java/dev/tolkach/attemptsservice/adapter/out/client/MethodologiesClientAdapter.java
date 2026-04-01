package dev.tolkach.attemptsservice.adapter.out.client;

import common.dto.*;
import dev.tolkach.attemptsservice.application.port.out.MethodologiesPort;
import feign.FeignException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MethodologiesClientAdapter implements MethodologiesPort {
    private final MethodologiesClient methodologiesClient;

    public MethodologiesClientAdapter(MethodologiesClient methodologiesClient) {
        this.methodologiesClient = methodologiesClient;
    }

    @Override
    public void validateScaleExists(UUID scaleId) {
        try {
            methodologiesClient.getScale(scaleId);
        } catch (FeignException.NotFound e) {
            throw new NoSuchElementException("Scale not found with id: " + scaleId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ScaleDto> getScalesByMethodologyId(UUID methodologyId) {
        SearchFilterDto filter = new SearchFilterDto();
        filter.setMethodologyId(methodologyId);
        return methodologiesClient.searchScales(filter);
    }

    @Override
    public List<ScaleQuestionDto> getScaleQuestionsByScaleIds(Collection<UUID> scaleIds) {
        List<ScaleQuestionDto> result = new ArrayList<>();
        for (UUID scaleId : scaleIds) {
            SearchFilterDto singleFilter = new SearchFilterDto();
            singleFilter.setScaleId(scaleId);
            result.addAll(methodologiesClient.searchScaleQuestions(singleFilter));
        }

        return result;
    }

    @Override
    public List<ScoreRangeDto> getScoreRangesByScaleIds(Collection<UUID> scaleIds) {
        List<ScoreRangeDto> result = new ArrayList<>();
        for (UUID sId : scaleIds) {
            SearchFilterDto filter = new SearchFilterDto();
            filter.setScaleId(sId);
            result.addAll(methodologiesClient.searchScoreRanges(filter));

        }
        return result;
    }
}