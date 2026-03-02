package dev.tolkach.testsservice.application.service;

import dev.tolkach.testsservice.application.model.AnswerOption;
import dev.tolkach.testsservice.application.port.in.AnswerOptionUseCase;
import dev.tolkach.testsservice.application.port.out.AnswerOptionRepository;
import dev.tolkach.testsservice.application.port.out.QuestionRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class AnswerOptionService implements AnswerOptionUseCase {

    private final AnswerOptionRepository answerOptionRepository;
    private final QuestionRepository questionRepository;

    public AnswerOptionService(AnswerOptionRepository answerOptionRepository, QuestionRepository questionRepository) {
        this.answerOptionRepository = answerOptionRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnswerOption> getAnswerOptionsByFilter(AnswerOption filter) {
        if (filter.getQuestionId() != null) {
            questionRepository.findById(filter.getQuestionId())
                    .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + filter.getQuestionId()));
        }
        return answerOptionRepository.findByFilter(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public AnswerOption getAnswerOptionById(UUID id) {
        return answerOptionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("AnswerOption not found with id: " + id));
    }

    @Override
    @Transactional
    public AnswerOption createUpdateAnswerOption(AnswerOption answerOption) {
        questionRepository.findById(answerOption.getQuestionId())
                .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + answerOption.getQuestionId()));

        AnswerOption checkFilter = new AnswerOption();
        checkFilter.setQuestionId(answerOption.getQuestionId());

        List<AnswerOption> existingInSameQuestion = answerOptionRepository.findByFilter(checkFilter);

        for (AnswerOption optionOfQuestion : existingInSameQuestion) {
            if (answerOption.getText().trim().equalsIgnoreCase(optionOfQuestion.getText().trim())) {
                throw new IllegalArgumentException(
                        "Answer option with the same text '" + answerOption.getText() + "' already exists for question " + answerOption.getQuestionId()
                );
            }
        }

        if (answerOption.getId() == null) {
            return answerOptionRepository.save(answerOption);
        } else {
            AnswerOption existing = answerOptionRepository.findById(answerOption.getId())
                    .orElseThrow(() -> new NoSuchElementException("AnswerOption not found with id: " + answerOption.getId()));

            existing.setText(answerOption.getText());
            existing.setQuestionId(answerOption.getQuestionId());
            existing.setScore(answerOption.getScore());

            return answerOptionRepository.save(existing);
        }
    }

    @Override
    @Transactional
    public void deleteAnswerOption(UUID id) {
        if (answerOptionRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("AnswerOption not found with id: " + id);
        }
        answerOptionRepository.deleteById(id);
    }
}
