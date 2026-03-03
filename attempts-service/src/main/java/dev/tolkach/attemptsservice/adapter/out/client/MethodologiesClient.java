package dev.tolkach.attemptsservice.adapter.out.client;

import common.dto.ScaleDto;
import common.dto.ScaleQuestionDto;
import common.dto.ScoreRangeDto;
import common.dto.SearchFilterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "methodologies-service")
public interface MethodologiesClient {

    @GetMapping("/api/scales/{id}")
    Object getScale(@PathVariable UUID id);

    @PostMapping("/api/scales/search")
    List<ScaleDto> searchScales(@RequestBody SearchFilterDto filter);

    @PostMapping("/api/scale-questions/search")
    List<ScaleQuestionDto> searchScaleQuestions(@RequestBody SearchFilterDto filter);

    @PostMapping("/api/score-ranges/search")
    List<ScoreRangeDto> searchScoreRanges(@RequestBody SearchFilterDto filter);
}
