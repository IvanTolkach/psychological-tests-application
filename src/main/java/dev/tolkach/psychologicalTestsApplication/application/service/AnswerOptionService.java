package dev.tolkach.psychologicalTestsApplication.application.service;

import dev.tolkach.psychologicalTestsApplication.domain.model.AnswerOption;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.AnswerOptionUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.AnswerOptionRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.QuestionRepository;

import java.util.List;
import java.util.UUID;

public class AnswerOptionService implements AnswerOptionUseCase {

    private final AnswerOptionRepository answerOptionRepository;
    private final QuestionRepository questionRepository;

    public AnswerOptionService(AnswerOptionRepository answerOptionRepository, QuestionRepository questionRepository) {
        this.answerOptionRepository = answerOptionRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<AnswerOption> getAnswerOptionsByFilter(AnswerOption filter) {
        if (filter.getQuestionId() != null) {
            questionRepository.findById(filter.getQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException("Question not found with id: " + filter.getQuestionId()));
        }
        return answerOptionRepository.findByFilter(filter);
    }

    @Override
    public AnswerOption createUpdateAnswerOption(AnswerOption answerOption) {
        questionRepository.findById(answerOption.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Question not found with id: " + answerOption.getQuestionId()));

        if (answerOption.getId() == null) {
            return answerOptionRepository.save(answerOption);
        } else {
            AnswerOption existing = answerOptionRepository.findById(answerOption.getId())
                    .orElseThrow(() -> new IllegalArgumentException("AnswerOption not found with id: " + answerOption.getId()));

            existing.setText(answerOption.getText());
            existing.setQuestionId(answerOption.getQuestionId());
            existing.setScore(answerOption.getScore());

            return answerOptionRepository.save(existing);
        }
    }

    @Override
    public void deleteAnswerOption(UUID id) {
        if (answerOptionRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("AnswerOption not found with id: " + id);
        }
        answerOptionRepository.deleteById(id);
    }
}
