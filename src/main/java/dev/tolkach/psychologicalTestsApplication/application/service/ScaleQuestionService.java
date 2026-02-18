package dev.tolkach.psychologicalTestsApplication.application.service;

import dev.tolkach.psychologicalTestsApplication.domain.model.ScaleQuestion;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.ScaleQuestionUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.QuestionRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.ScaleQuestionRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.ScaleRepository;

import java.util.List;
import java.util.UUID;

public class ScaleQuestionService implements ScaleQuestionUseCase {

    private final ScaleQuestionRepository scaleQuestionRepository;
    private final ScaleRepository scaleRepository;
    private final QuestionRepository questionRepository;

    public ScaleQuestionService(ScaleQuestionRepository scaleQuestionRepository, ScaleRepository scaleRepository, QuestionRepository questionRepository) {
        this.scaleQuestionRepository = scaleQuestionRepository;
        this.scaleRepository = scaleRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<ScaleQuestion> getScaleQuestionsByFilter(ScaleQuestion filter) {
        if (filter.getScaleId() != null) {
            scaleRepository.findById(filter.getScaleId())
                    .orElseThrow(() -> new IllegalArgumentException("Scale not found with id: " + filter.getScaleId()));
        }
        if (filter.getQuestionId() != null) {
            questionRepository.findById(filter.getQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException("Question not found with id: " + filter.getQuestionId()));
        }
        return scaleQuestionRepository.findByFilter(filter);
    }

    @Override
    public ScaleQuestion createScaleQuestion(ScaleQuestion scaleQuestion) {
        scaleRepository.findById(scaleQuestion.getScaleId())
                .orElseThrow(() -> new IllegalArgumentException("Scale not found with id: " + scaleQuestion.getScaleId()));

        questionRepository.findById(scaleQuestion.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Question not found with id: " + scaleQuestion.getQuestionId()));

        ScaleQuestion checkFilter = new ScaleQuestion();
        checkFilter.setScaleId(scaleQuestion.getScaleId());
        checkFilter.setQuestionId(scaleQuestion.getQuestionId());

        boolean alreadyExists = !scaleQuestionRepository.findByFilter(checkFilter).isEmpty();

        if (alreadyExists) {
            throw new IllegalArgumentException(
                    "ScaleQuestion link already exists: scale " + scaleQuestion.getScaleId() +
                            " - question " + scaleQuestion.getQuestionId()
            );
        }

        return scaleQuestionRepository.save(scaleQuestion);
    }

    @Override
    public void deleteScaleQuestion(UUID id) {
        if (scaleQuestionRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("ScaleQuestion link not found with id: " + id);
        }
        scaleQuestionRepository.deleteById(id);
    }
}
